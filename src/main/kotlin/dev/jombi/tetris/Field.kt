package dev.jombi.tetris

import dev.jombi.tetris.block.Block
import dev.jombi.tetris.block.BlockCollection
import dev.jombi.tetris.block.BlockColor
import dev.jombi.tetris.screen.impl.GuiGameOver
import dev.jombi.tetris.tetrimino.Tetrimino
import dev.jombi.tetris.tetrimino.impl.*

class Field {
    val blockX = 10
    val blockY = 20
    private val field = BlockCollection(blockX, blockY)

    fun drawField(width: Int, height: Int) {
        val x = 85f
        val y = 15f
        val cWidth = width - 170f
        val cHeight = height - 30f
        Drawer.drawRect(x, y, cWidth, cHeight, 0xff202020.toInt())
        Drawer.drawOutLine(x, y, cWidth, cHeight, 0xffffffff.toInt())
        val sWidth = cWidth / blockX
        for (i in 1 until blockX) Drawer.drawLine(
            x + i * sWidth,
            y + 1f,
            x + +i * sWidth,
            y + cHeight - 1f,
            0xff424242.toInt()
        )
        val sHeight = cHeight / blockY
//        println("$sWidth $sHeight")
        for (i in 1 until blockY) Drawer.drawLine(
            x + 1f,
            y + i * sHeight,
            x + cWidth - 1f,
            y + i * sHeight,
            0xff424242.toInt()
        )

        for (block in field) {
            if (block == null) continue
            val bX = x + block.x * sWidth
            val bY = y + block.y * sHeight
            Drawer.drawRect(bX + 1f, bY + 1f, sWidth - 2f, sHeight - 2f, block.color.colorCode)
        }

        val arrays = (currentMino ?: return).blocks[minoSpin]
        for (blocks in arrays) {
            for (block in blocks) {
                if (block == null) continue
                val bX = x + (minoPosX + block.x) * sWidth
                val bY = y + (minoPosY + block.y) * sHeight
                Drawer.drawRect(bX + 1f, bY + 1f, sWidth - 2f, sHeight - 2f, block.color.colorCode)
            }
        }
    }

    fun drawHoldUI() {
        val x = 15f
        val y = 15f

        val cWidth = 50f
        val cHeight = 50f

        Drawer.drawRect(x, y, cWidth, cHeight, 0xff202020.toInt())
        Drawer.drawOutLine(x, y, cWidth, cHeight, 0xffffffff.toInt())

        val sWidth = cWidth / 4
        for (i in 1 until 4) Drawer.drawLine(
            x + i * sWidth,
            y + 1f,
            x + i * sWidth,
            y + cHeight - 1f,
            0xff424242.toInt()
        )
        val sHeight = cHeight / 4
//        println("$sWidth $sHeight")
        for (i in 1 until 4) Drawer.drawLine(
            x + 1f,
            y + i * sHeight,
            x + cWidth - 1f,
            y + i * sHeight,
            0xff424242.toInt()
        )

        val mino = holdingMino ?: return
        val blocksArray = mino.blocks[0].flatten()
        for (block in blocksArray) {
            if (block == null) continue
            val bX = x + block.x * sWidth
            val bY = y + block.y * sHeight
            Drawer.drawRect(bX + 1f, bY + 1f, sWidth - 2f, sHeight - 2f, block.color.colorCode)
        }
    }

    var canHold = false

    fun holdMino() {
        if (!canHold) return
        canHold = false
        if (holdingMino == null) holdingMino = fetchNewMino()
        val temp = holdingMino
        holdingMino = currentMino
        currentMino = temp
        resetPosition()
    }

    private val minos: ArrayList<Tetrimino> = arrayListOf()
    var holdingMino: Tetrimino? = null
    var currentMino: Tetrimino? = null
    var minoPosY = 0
        set(value) {
            if (field == value) return
            if (value in -2 until blockY && !checkFloor(currentMino!!, minoSpin, value)) {
                field = value
                lastMoving = 0
            }
        }
    var minoPosX = 0
        set(value) {
            if (!isSide(currentMino ?: return, minoSpin, value)) {
                field = value
            }
        }

    fun minoSpin(type: SpinType) {
        when (type) {
            SpinType.RESET -> minoSpin = 0
            SpinType.FORWARD -> if (checkFreeSpace(currentMino ?: return, minoSpin, (minoSpin + 1).clampRev(0, 3))) minoSpin = (minoSpin + 1).clampRev(0, 3)
            SpinType.BACKWARD -> if (checkFreeSpace(currentMino ?: return, minoSpin, (minoSpin - 1).clampRev(0, 3))) minoSpin = (minoSpin - 1).clampRev(0, 3)
            SpinType.DOUBLEWARD -> if (check180WiseSpace(currentMino ?: return, minoSpin, 2)) {
                minoSpin += 2
                if (minoSpin > 3) minoSpin -= 4
            }
        }
    }

    fun Int.clampRev(min: Int, max: Int) = if (min > this) max else if (max < this) min else this

    var minoSpin = 0

    fun downLine() {
        val a = Array(blockY) { arrayOfNulls<BlockColor>(blockX) }
        for (block in field) {
            block ?: continue
            a[block.y][block.x] = block.color
        }
        if (a.any { it.filterNotNull().size == it.size }) {
            field.clearField()
            var i = blockY - 1
            for (blockColors in a.filter { it.any { it == null } }.reversed()) {
                for ((x, color) in blockColors.withIndex()) {
                    field.place(x, i, color ?: continue)
                }
                i--
            }
        }
    }

    private fun isSide(mino: Tetrimino, rotate: Int, targetX: Int): Boolean {
        val check = mino.blocks[rotate]
        if (fieldCheck(check.flatten(), targetX, minoPosY)) return true
        var baseX = 5
        val maxX = hashSetOf<Int>()
        for (blocks in check) {
            maxX.addAll(blocks.filterNotNull().map { it.x })
            val block = blocks.firstNotNullOfOrNull { it } ?: continue
            baseX = minOf(baseX, block.x)
        }
        val leftCheck = targetX + baseX
        val rightCheck = leftCheck + maxX.size
        return leftCheck < 0 || rightCheck > blockX
    }

    private fun fieldCheck(flatten: List<Block?>, targetX: Int, targetY: Int): Boolean {
        val notNullMap = field.filterNotNull().map { it.x to it.y }
        if (flatten.filterNotNull().map { targetX + it.x to targetY + it.y }
                .any { notNullMap.contains(it) }) return true
        return false
    }

    fun check180WiseSpace(mino: Tetrimino, targetIndex: Int, incrementation: Int): Boolean {
        var rotate = targetIndex + incrementation
        if (rotate > 3) rotate -= 4
        val flatten = mino.blocks[rotate].flatten()
        val isIMino = mino is IMino
        val array = if (isIMino) RotateOffsets.WALLKICK_I_180[targetIndex] else RotateOffsets.WALLKICK_NORMAL_180[targetIndex]
        for (index in array.indices) {
            val (x, y) = array[index]
            if (!fieldCheck(flatten, minoPosX + x, minoPosY + y) && !isSide(mino, rotate, minoPosX + x)) {
                minoPosX += x
                minoPosY += y
                return true
            }
        }
        return false
    }

    fun checkFreeSpace(mino: Tetrimino, currentRotate: Int, targetRotate: Int): Boolean {
        val flatten = mino.blocks[targetRotate].flatten()
        for (index in 0..3) {
            val isIMino = mino is IMino
            val rotDiff = targetRotate - currentRotate
            val primaryRot = rotDiff == 1 || rotDiff < -1
            val backwardRot = rotDiff == -1 || rotDiff > 1
            val (x, y) = if (primaryRot) {
                if (isIMino) RotateOffsets.WALLKICK_I_NORMAL[currentRotate][index] else RotateOffsets.WALLKICK_NORMAL[currentRotate][index]
            } else if (backwardRot) {
                if (isIMino) RotateOffsets.WALLKICK_I_REVERSED[targetRotate][index] else RotateOffsets.WALLKICK_REVERSED[targetRotate][index]
            } else return true
            if (!fieldCheck(flatten, minoPosX + x, minoPosY + y) && !isSide(mino, targetRotate, minoPosX + x)) {
                minoPosX += x
                minoPosY += y
                return true
            }
        }
        return false
    }

    var lastMoving = 0


    fun hardDrop() {
        while (!checkFloor(currentMino ?: return, minoSpin, minoPosY + 1)) down()
        lastMoving = 21
        placeMino()
    }

    fun placeMino() {
        if (currentMino != null)
            try {
                if (lastMoving > 20 && checkFloor(currentMino!!, minoSpin, minoPosY + 1)) {
                    for (blocks in currentMino!!.blocks[minoSpin]) for (block in blocks) {
                        if (block == null) continue
                        field.place(minoPosX + block.x, minoPosY + block.y, block.color)
                    }
                    currentMino = null
                }
            } catch (e: ArrayIndexOutOfBoundsException) {
                GLTetrisGame.instance().displayScreen(GuiGameOver())
                GLTetrisGame.instance().finalizeGame()
                System.gc()
            }
    }

    fun down() {
        minoPosY++
    }

    fun resetPosition() {
        minoPosY = -2
        minoPosX = blockX / 2 - 2
        minoSpin(SpinType.RESET)
    }

    fun updateMino() {
        placeMino()
        if (currentMino == null) {
            currentMino = fetchNewMino()
            resetPosition()
            canHold = true
            return
        }
        if (lastMoving > 20) down()
    }

    private fun fetchNewMino(): Tetrimino {
        if (minos.isEmpty()) reset()
        val min = minos.random()
        minos.remove(min)
        return min
    }

    fun checkFloor(mino: Tetrimino, rotate: Int, targetY: Int): Boolean {
        val flatten = mino.blocks[rotate].flatten()
        if (fieldCheck(flatten, minoPosX, targetY)) return true
        var baseY = 5
        val maxX = hashSetOf<Int>()
        val maxY = hashSetOf<Int>()
        for (block in flatten) {
            if (block == null) continue
            maxX.add(block.x)
            maxY.add(block.y)
            baseY = minOf(baseY, block.y)
        }
        return targetY + maxY.size + baseY > blockY
    }

    private fun reset() {
        minos.clear()
        minos.addAll(arrayOf(IMino(), JMino(), LMino(), OMino(), SMino(), TMino(), ZMino()))
    }
}
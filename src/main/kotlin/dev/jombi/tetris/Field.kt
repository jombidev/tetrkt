package dev.jombi.tetris

import dev.jombi.tetris.block.Block
import dev.jombi.tetris.block.BlockCollection
import dev.jombi.tetris.block.BlockColor
import dev.jombi.tetris.tetrimino.Tetrimino
import dev.jombi.tetris.tetrimino.impl.*

class Field {
    val blockX = 10
    val blockY = 20
    private val field = BlockCollection(blockX, blockY)

    fun drawField(width: Int, height: Int) {
        val x = 15f
        val y = 15f
        val cWidth = width - 130f
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
        for (i in 1 until blockY) Drawer.drawLine(
            x + 1f,
            y + i * sHeight,
            x + cWidth,
            y + i * sHeight - 1f,
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

    private val minos: ArrayList<Tetrimino> = arrayListOf()
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

    var minoSpin = 0
        set(value) {
            if (value == -999) {
                field = 0
                return
            }
            if (checkFreeSpace(currentMino ?: return, field, value)) {
                field = value
            }
        }

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

    fun checkFreeSpace(mino: Tetrimino, currentRotate: Int, targetRotate: Int): Boolean {
        val flatten = mino.blocks[targetRotate].flatten()
        for (index in 0..3) {
            val isIMino = mino is IMino
            val rotDiff = targetRotate - currentRotate
            val primaryRot = rotDiff == 1 || rotDiff < -1
            val backwardRot = rotDiff == -1 || rotDiff > 1
            val (x, y) = if (primaryRot) {
                if (isIMino) RotateOffsets.spinTestINormal[currentRotate][index] else RotateOffsets.spinTestNormal[currentRotate][index]
            } else if (backwardRot) {
                if (isIMino) RotateOffsets.spinTestIReversed[targetRotate][index] else RotateOffsets.spinTestReversed[targetRotate][index]
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

    fun placeMino() {
        if (currentMino != null)
            if (lastMoving > 20 && checkFloor(currentMino!!, minoSpin, minoPosY + 1)) {
                for (blocks in currentMino!!.blocks[minoSpin]) for (block in blocks) {
                    if (block == null) continue
                    field.place(minoPosX + block.x, minoPosY + block.y, block.color)
                }
                currentMino = null
            }
    }

    fun down() {
        minoPosY++
    }

    fun updateMino() {
        placeMino()
        if (currentMino == null) {
            if (minos.isEmpty()) reset()
            val min = minos.random()
            minos.remove(min)
            currentMino = min
            minoPosY = -2
            minoPosX = blockX / 2 - 2
            minoSpin = -999
            return
        }
        if (lastMoving > 20) down()
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
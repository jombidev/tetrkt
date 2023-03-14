package dev.jombi.tetris.screen.impl

import dev.jombi.tetris.Drawer
import dev.jombi.tetris.GLTetrisGame
import dev.jombi.tetris.Timer
import dev.jombi.tetris.font.FontFactory
import dev.jombi.tetris.font.FontType
import dev.jombi.tetris.getSystemTime
import dev.jombi.tetris.screen.GuiScreen
import dev.jombi.tetris.screen.overlay.Button
import dev.jombi.tetris.tetrimino.impl.*

class GuiMainMenu private constructor() : GuiScreen() {
    companion object {
        private val instance = GuiMainMenu()
        @JvmStatic
        fun getInstance() = instance
    }
    override fun initGui() {
        val halfWidth = GLTetrisGame.instance().WIDTH / 2.0
        val halfHeight = GLTetrisGame.instance().HEIGHT / 2.0
        buttons.add(Button("Start", halfWidth, halfHeight) {
            GLTetrisGame.instance().initGame()
            GLTetrisGame.instance().displayScreen(null)
        })
        buttons.add(Button("Exit", halfWidth, halfHeight + 40.0) {
            GLTetrisGame.instance().exitGame()
        })
    }

    private val timer = Timer(10.0f, 0L)
    private val minos = arrayListOf(IMino(), JMino(), LMino(), OMino(), SMino(), TMino(), ZMino())
    private var currentMino = minos.random()

    override fun drawScreen(mouseX: Double, mouseY: Double) {
        super.drawScreen(mouseX, mouseY)
        val halfWidth = GLTetrisGame.instance().WIDTH / 2.0
        val TARGET_FONT = FontFactory.getFont(FontType.ARIAL, 48)
        val (fWidth, fHeight) = TARGET_FONT.getStringBound("TetrKt")


        val f = timer.advanceTime(getSystemTime())

        for (i in 0 until minOf(10, f)) {
            currentMino = minos.random()
        }

        val blocksArray = currentMino.blocks[0].flatten()
        val WH = 25.0
        for (block in blocksArray) {
            if (block == null) continue
            val bX = halfWidth + WH / 2.0 - block.x * WH
            val bY = 80.0f + block.y * WH
            Drawer.drawRect(bX + 1f, bY + 1f, WH - 2f, WH - 2f, block.color.colorCode)
        }
        TARGET_FONT.drawString("TetrKt", halfWidth - fWidth / 2.0, 30.0 + fHeight, 0xffffffff.toInt())
    }
}
package dev.jombi.tetris.screen.impl

import dev.jombi.tetris.GLTetrisGame
import dev.jombi.tetris.screen.GuiScreen
import dev.jombi.tetris.screen.overlay.Button

class GuiGameOver : GuiScreen() {
    override fun initGui() {
        val halfWidth = GLTetrisGame.instance().WIDTH / 2.0
        val halfHeight = GLTetrisGame.instance().HEIGHT / 2.0
        buttons.add(Button("Retry", halfWidth, halfHeight) {
            GLTetrisGame.instance().initGame()
            GLTetrisGame.instance().displayScreen(null)
        })

        buttons.add(Button("Main menu", halfWidth, halfHeight + 40.0) {
            GLTetrisGame.instance().displayScreen(GuiMainMenu.getInstance())
        })
    }

    override fun drawScreen(mouseX: Double, mouseY: Double) {
        super.drawScreen(mouseX, mouseY)
    }
}
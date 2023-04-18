package dev.jombi.tetris.screen.impl

import dev.jombi.tetris.GLTetrisGame
import dev.jombi.tetris.screen.GuiScreen
import dev.jombi.tetris.screen.overlay.Button

class GuiGameOver : GuiScreen() {
    override fun initGui() {
        val halfWidth = GLTetrisGame.getInstance().WIDTH / 2.0
        val halfHeight = GLTetrisGame.getInstance().HEIGHT / 2.0
        buttons.add(Button("Retry", halfWidth, halfHeight) {
            GLTetrisGame.getInstance().initGame()
            GLTetrisGame.getInstance().displayScreen(null)
        })

        buttons.add(Button("Main menu", halfWidth, halfHeight + 40.0) {
            GLTetrisGame.getInstance().displayScreen(GuiMainMenu.getInstance())
        })
    }

    override fun drawScreen(mouseX: Double, mouseY: Double) {
        super.drawScreen(mouseX, mouseY)
    }
}
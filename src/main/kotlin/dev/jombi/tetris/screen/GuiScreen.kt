package dev.jombi.tetris.screen

import dev.jombi.tetris.screen.overlay.Button

abstract class GuiScreen : IDrawable, ITypeable, IClickable {
    val buttons = ArrayList<Button>()
    open fun initGui() {}
    override fun drawScreen(mouseX: Double, mouseY: Double) {
        for (button in buttons) button.drawScreen(mouseX, mouseY)
    }
    override fun mouseClicked(mouseBtn: Int, mouseX: Double, mouseY: Double) {
        for (button in buttons) button.mouseClicked(mouseBtn, mouseX, mouseY)
    }
}
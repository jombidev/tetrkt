package dev.jombi.tetris.screen.overlay

import dev.jombi.tetris.Drawer
import dev.jombi.tetris.font.FontFactory
import dev.jombi.tetris.font.FontType
import dev.jombi.tetris.screen.IClickable
import dev.jombi.tetris.screen.IDrawable

class Button(val name: String, xPos: Number, yPos: Number, private val clickCallback: () -> Any? = {}) : IDrawable, IClickable {
    private val TARGET_FONT = FontFactory.getFont(FontType.ARIAL, 24)
    private val btnX: Double
    private val btnY: Double
    private val btnWidth: Double
    private val btnHeight: Double = TARGET_FONT.FONT_HEIGHT + 12.0
    init {
        val width = TARGET_FONT.getStringWidth(name) * 2.0
        btnX = xPos.toDouble() - width / 2.0
        btnY = yPos.toDouble() - TARGET_FONT.FONT_HEIGHT
        btnWidth = width
    }

    override fun drawScreen(mouseX: Double, mouseY: Double) {
        Drawer.drawRect(btnX, btnY, btnWidth, btnHeight, if (isHovered(mouseX, mouseY)) 0xff646464.toInt() else 0xff424242.toInt())
        val (width, _) = TARGET_FONT.getStringBound(name)
        TARGET_FONT.drawString(name, btnX + btnWidth / 2.0 - width / 2.0, btnY + TARGET_FONT.FONT_HEIGHT + 2.0, 0xffffffff.toInt())
    }

    override fun mouseClicked(mouseBtn: Int, mouseX: Double, mouseY: Double) {
        if (isHovered(mouseX, mouseY)) clickCallback()
    }

    private fun isHovered(mouseX: Double, mouseY: Double) = mouseX in btnX .. btnX + btnWidth && mouseY in btnY .. btnY + btnHeight
}
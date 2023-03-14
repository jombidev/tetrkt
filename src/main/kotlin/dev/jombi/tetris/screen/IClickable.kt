package dev.jombi.tetris.screen

interface IClickable {
    fun mouseClicked(mouseBtn: Int, mouseX: Double, mouseY: Double) {}
    fun mouseReleased(mouseBtn: Int, mouseX: Double, mouseY: Double) {}
}
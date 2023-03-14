package dev.jombi.tetris.input

import org.lwjgl.BufferUtils
import org.lwjgl.glfw.GLFW

class Mouse {
    fun updateMouse(window: Long) {
        val x = BufferUtils.createDoubleBuffer(1)
        val y = BufferUtils.createDoubleBuffer(1)

        GLFW.glfwGetCursorPos(window, x, y)
        x.rewind()
        y.rewind()

        mouseX = x.get()
        mouseY = y.get()
    }
    private var mouseX = 0.0
    private var mouseY = 0.0
    fun mouseX() = mouseX
    fun mouseY() = mouseY
}
package dev.jombi.tetris

import dev.jombi.tetris.input.Key
import dev.jombi.tetris.input.Mouse
import dev.jombi.tetris.screen.GuiScreen
import dev.jombi.tetris.screen.impl.GuiMainMenu
import org.lwjgl.glfw.Callbacks.glfwFreeCallbacks
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.glfw.GLFWErrorCallback
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11.*
import org.lwjgl.system.MemoryUtil.NULL

class GLTetrisGame {
    companion object {
        private val instance = GLTetrisGame()
        @JvmStatic
        fun getInstance() = instance
    }

    private val gameTimer = Timer(20.0f, 0L)
    private val keyTimer = Timer(40.0f, 0)
    private var window  = 0L
    private var field: Field? = null
    val WIDTH = 400
    val HEIGHT = 490

    private var currentScreen: GuiScreen? = null
    private val mouseHelper = Mouse()
    fun displayScreen(s: GuiScreen?) {
        currentScreen = s
        currentScreen?.initGui()
    }
    fun exitGame() {
        glfwSetWindowShouldClose(window, true)
    }
    fun initGame() {
        field = Field()
        field!!.updateMino()
    }
    private fun updateGame() {
        if (field == null) return
        field!!.drawField(WIDTH, HEIGHT)
        field!!.drawHoldUI()
    }
    fun finalizeGame() {
        field = null
    }

    fun init() {
        GLFWErrorCallback.createPrint(System.err).set()
        if (!glfwInit()) throw IllegalStateException("Failed to initialize GLFW")
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE)
        window = glfwCreateWindow(WIDTH, HEIGHT, "TetrKt", NULL, NULL)
        if (window == NULL) throw IllegalStateException("Failed to initialize Window")
        glfwSetKeyCallback(window) { window: Long, key: Int, _: Int, action: Int, _: Int ->
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) glfwSetWindowShouldClose(window, true)
        }
        glfwMakeContextCurrent(window)
        glfwSwapInterval(1)
        glfwShowWindow(window)
    }

    private val keys = arrayListOf<Key>()

    fun loop() {
        GL.createCapabilities()

        glClearColor(.2f, .2f, .2f, 1f)

        inputReadingThread()
        displayScreen(GuiMainMenu.getInstance())

        while (!glfwWindowShouldClose(window)) {
            glfwPollEvents()

            val e = gameTimer.advanceTime(getSystemTime())
            val f = keyTimer.advanceTime(getSystemTime())

            for (i in 0 until minOf(10, e))
                runTick()

            for (i in 0 until minOf(10, f))
                consumeKeys()

            glMatrixMode(GL_PROJECTION)
            glLoadIdentity()
            glOrtho(0.0, WIDTH.toDouble(), HEIGHT.toDouble(), 0.0, -10.0, 10.0)
            glMatrixMode(GL_MODELVIEW)
            glLoadIdentity()
            glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)

            mouseHelper.updateMouse(window)

            if (currentScreen != null) {
                currentScreen!!.drawScreen(mouseHelper.mouseX(), mouseHelper.mouseY())
            }
            updateGame()

            glfwSwapBuffers(window)
        }
    }

    var ticksExisted = 0
    fun runTick() {
        ticksExisted++
        val f = field ?: return
        f.lastMoving++
        f.updateMino()
        f.downLine()
    }

    fun consumeKeys() {
        for (k in keys) {
            currentScreen?.keyTyped(k)
            val (key, ms) = k
            val delay = getSystemTime() - ms
            if (k.isFirst || delay > 150L) {
                val f = field ?: return
                k.isFirst = false
                when (key) {
                    GLFW_KEY_LEFT -> {
                        f.minoPosX--
                    }

                    GLFW_KEY_RIGHT -> {
                        f.minoPosX++
                    }

                    GLFW_KEY_Z -> {
                        f.minoSpin(SpinType.BACKWARD)
                    }

                    GLFW_KEY_X -> {
                        f.minoSpin(SpinType.DOUBLEWARD)
                    }

                    GLFW_KEY_C -> {
                        f.minoSpin(SpinType.FORWARD)
                    }

                    GLFW_KEY_SPACE -> {
                        f.holdMino()
                    }

                    GLFW_KEY_DOWN -> {
                        f.down()
                    }

                    GLFW_KEY_UP -> {
                        f.hardDrop()
                    }
                }
            }
        }
    }

    fun inputReadingThread() {
        glfwSetKeyCallback(window) { window, key, scancode, action, mods ->
            if (action == 1) keys.add(Key(key, getSystemTime())) else if (action == 0) keys.removeIf { it.key == key }
        }

        glfwSetMouseButtonCallback(window) { _, button, action, mods ->
            if (action == 1) {
                currentScreen?.mouseClicked(button, mouseHelper.mouseX(), mouseHelper.mouseY())
            } else if (action == 0) {
                currentScreen?.mouseReleased(button, mouseHelper.mouseX(), mouseHelper.mouseY())
            }
        }
    }

    fun clear() {
        if (window != 0L) {
            glfwFreeCallbacks(window)
            glfwDestroyWindow(window)
        }
        glfwTerminate()
        glfwSetErrorCallback(null)?.free()
    }
}
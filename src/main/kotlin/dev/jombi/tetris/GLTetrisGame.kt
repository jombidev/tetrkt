package dev.jombi.tetris

import dev.jombi.tetris.key.Key
import org.lwjgl.glfw.Callbacks.glfwFreeCallbacks
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.glfw.GLFWErrorCallback
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11.*
import org.lwjgl.system.MemoryUtil.NULL
import kotlin.properties.Delegates


class GLTetrisGame(val timer: Timer) {
    private val keyTimer = Timer(40.0f, 0)
    private var window by Delegates.notNull<Long>()
    private val field = Field()
    val WIDTH = 400
    val HEIGHT = 490
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

    val keys = arrayListOf<Key>()

    fun loop() {
        GL.createCapabilities()
        glClearColor(.2f, .2f, .2f, 1f)

        inputReadingThread()
        field.updateMino()

        while (!glfwWindowShouldClose(window)) {
            glfwPollEvents()

            val e = timer.advanceTime(getSystemTime())
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

            field.drawField(WIDTH, HEIGHT)
            field.drawHoldUI()

            glfwSwapBuffers(window)
        }
    }

    var ticksExisted = 0
    fun runTick() {
        ticksExisted++
        field.lastMoving++
        field.updateMino()
        field.downLine()
    }

    fun consumeKeys() {
        for (k in keys) {
            val (key, ms) = k
            val delay = getSystemTime() - ms
            if (k.isFirst || delay > 150L) {
                k.isFirst = false
                when (key) {
                    GLFW_KEY_LEFT -> {
                        field.minoPosX--
                    }

                    GLFW_KEY_RIGHT -> {
                        field.minoPosX++
                    }

                    GLFW_KEY_Z -> {
                        field.minoSpin = (field.minoSpin - 1).clampRev(0, 3)
                    }

                    GLFW_KEY_C -> {
                        field.minoSpin = (field.minoSpin + 1).clampRev(0, 3)

                    }

                    GLFW_KEY_SPACE -> {
                        field.holdMino()
                    }

                    GLFW_KEY_DOWN -> {
                        field.down()
                    }

                    GLFW_KEY_UP -> {
                        field.hardDrop()
                    }
                }
            }
        }
    }

    fun inputReadingThread() {
        glfwSetKeyCallback(window) { window, key, scancode, action, mods ->
            if (action == 1) keys.add(Key(key, getSystemTime())) else if (action == 0) keys.removeIf { it.key == key }
        }
    }

    fun Int.clampRev(min: Int, max: Int) = if (min > this) max else if (max < this) min else this

    fun clear() {
        glfwFreeCallbacks(window)
        glfwDestroyWindow(window)

        glfwTerminate()
        glfwSetErrorCallback(null)?.free()
    }
}
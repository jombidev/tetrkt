package dev.jombi.tetris

fun main() {
    val tetrKt = GLTetrisGame(TIMER)
    tetrKt.init()
    tetrKt.loop()
    tetrKt.clear()
}

private val TIMER = Timer(20.0f, 0L)

fun getSystemTime() = System.nanoTime() / 1000000L
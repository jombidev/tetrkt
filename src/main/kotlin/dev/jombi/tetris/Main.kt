package dev.jombi.tetris

fun main() {
    val tetrKt = GLTetrisGame.instance()
    try {
        tetrKt.init()
        tetrKt.loop()
    } finally {
        tetrKt.clear()
    }
}

fun getSystemTime() = System.nanoTime() / 1000000L
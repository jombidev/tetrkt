package dev.jombi.tetris

import java.lang.Exception

fun main() {
    val tetrKt = GLTetrisGame.getInstance()
    try {
        tetrKt.init()
        tetrKt.loop()
    } catch (e: Exception) {
        e.printStackTrace()
        println("Error while loop.")
    } finally {
        tetrKt.clear()
    }
}

fun getSystemTime() = System.nanoTime() / 1000000L
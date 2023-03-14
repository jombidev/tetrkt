package dev.jombi.tetris.screen

import dev.jombi.tetris.input.Key

interface ITypeable {
    fun keyTyped(key: Key) {}
}
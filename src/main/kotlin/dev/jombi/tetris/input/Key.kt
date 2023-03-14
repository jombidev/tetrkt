package dev.jombi.tetris.input

data class Key(val key: Int, val ms: Long, var isFirst: Boolean = true)
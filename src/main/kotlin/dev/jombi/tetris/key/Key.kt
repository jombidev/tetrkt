package dev.jombi.tetris.key

data class Key(val key: Int, val ms: Long, var isFirst: Boolean = true)
package dev.jombi.tetris

object RotateOffsets {
    val spinTestNormal = arrayOf(
        arrayOf(arrayOf(0, 0), arrayOf(-1, 0), arrayOf(-1, 1), arrayOf(0, -2), arrayOf(-1, -2)),
        arrayOf(arrayOf(0, 0), arrayOf(1, 0), arrayOf(1, -1), arrayOf(0, 2), arrayOf(1, 2)),
        arrayOf(arrayOf(0, 0), arrayOf(1, 0), arrayOf(1, 1), arrayOf(0, -2), arrayOf(1, -2)),
        arrayOf(arrayOf(0, 0), arrayOf(-1, 0), arrayOf(-1, -1), arrayOf(0, 2), arrayOf(-1, 2))
    )
    val spinTestINormal = arrayOf(
        arrayOf(arrayOf(0, 0), arrayOf(-2, 0), arrayOf(1, 0), arrayOf(-2, -1), arrayOf(1, 2)),
        arrayOf(arrayOf(0, 0), arrayOf(-1, 0), arrayOf(2, 0), arrayOf(-1, 2), arrayOf(2, -1)),
        arrayOf(arrayOf(0, 0), arrayOf(2, 0), arrayOf(-1, 0), arrayOf(2, 1), arrayOf(-1, -2)),
        arrayOf(arrayOf(0, 0), arrayOf(1, 0), arrayOf(-2, 0), arrayOf(1, -2), arrayOf(-2, 1))
    )
    val spinTestReversed = arrayOf(
        arrayOf(arrayOf(0, 0), arrayOf(1, 0), arrayOf(1, -1), arrayOf(0, 2), arrayOf(1, 2)),
        arrayOf(arrayOf(0, 0), arrayOf(-1, 0), arrayOf(-1, 1), arrayOf(0, -2), arrayOf(-1, -2)),
        arrayOf(arrayOf(0, 0), arrayOf(-1, 0), arrayOf(-1, -1), arrayOf(0, 2), arrayOf(-1, 2)),
        arrayOf(arrayOf(0, 0), arrayOf(1, 0), arrayOf(1, 1), arrayOf(0, -2), arrayOf(1, -2))
    )
    val spinTestIReversed = arrayOf(
        arrayOf(arrayOf(0, 0), arrayOf(2, 0), arrayOf(-1, 0), arrayOf(2, 1), arrayOf(-1, -2)),
        arrayOf(arrayOf(0, 0), arrayOf(1, 0), arrayOf(-2, 0), arrayOf(1, -2), arrayOf(-2, 1)),
        arrayOf(arrayOf(0, 0), arrayOf(-2, 0), arrayOf(1, 0), arrayOf(-2, -1), arrayOf(1, 2)),
        arrayOf(arrayOf(0, 0), arrayOf(-1, 0), arrayOf(2, 0), arrayOf(-1, 2), arrayOf(2, -1))
    )
}
package dev.jombi.tetris

object RotateOffsets {
    val WALLKICK_NORMAL = arrayOf(
        arrayOf(arrayOf(0, 0), arrayOf(-1, 0), arrayOf(-1, 1), arrayOf(0, -2), arrayOf(-1, -2)),
        arrayOf(arrayOf(0, 0), arrayOf(1, 0), arrayOf(1, -1), arrayOf(0, 2), arrayOf(1, 2)),
        arrayOf(arrayOf(0, 0), arrayOf(1, 0), arrayOf(1, 1), arrayOf(0, -2), arrayOf(1, -2)),
        arrayOf(arrayOf(0, 0), arrayOf(-1, 0), arrayOf(-1, -1), arrayOf(0, 2), arrayOf(-1, 2))
    )
    val WALLKICK_I_NORMAL = arrayOf(
        arrayOf(arrayOf(0, 0), arrayOf(-2, 0), arrayOf(1, 0), arrayOf(-2, -1), arrayOf(1, 2)),
        arrayOf(arrayOf(0, 0), arrayOf(-1, 0), arrayOf(2, 0), arrayOf(-1, 2), arrayOf(2, -1)),
        arrayOf(arrayOf(0, 0), arrayOf(2, 0), arrayOf(-1, 0), arrayOf(2, 1), arrayOf(-1, -2)),
        arrayOf(arrayOf(0, 0), arrayOf(1, 0), arrayOf(-2, 0), arrayOf(1, -2), arrayOf(-2, 1))
    )
    val WALLKICK_REVERSED = arrayOf(
        arrayOf(arrayOf(0, 0), arrayOf(1, 0), arrayOf(1, -1), arrayOf(0, 2), arrayOf(1, 2)),
        arrayOf(arrayOf(0, 0), arrayOf(-1, 0), arrayOf(-1, 1), arrayOf(0, -2), arrayOf(-1, -2)),
        arrayOf(arrayOf(0, 0), arrayOf(-1, 0), arrayOf(-1, -1), arrayOf(0, 2), arrayOf(-1, 2)),
        arrayOf(arrayOf(0, 0), arrayOf(1, 0), arrayOf(1, 1), arrayOf(0, -2), arrayOf(1, -2))
    )
    val WALLKICK_I_REVERSED = arrayOf(
        arrayOf(arrayOf(0, 0), arrayOf(2, 0), arrayOf(-1, 0), arrayOf(2, 1), arrayOf(-1, -2)),
        arrayOf(arrayOf(0, 0), arrayOf(1, 0), arrayOf(-2, 0), arrayOf(1, -2), arrayOf(-2, 1)),
        arrayOf(arrayOf(0, 0), arrayOf(-2, 0), arrayOf(1, 0), arrayOf(-2, -1), arrayOf(1, 2)),
        arrayOf(arrayOf(0, 0), arrayOf(-1, 0), arrayOf(2, 0), arrayOf(-1, 2), arrayOf(2, -1))
    )
    val WALLKICK_NORMAL_180 = arrayOf(
        arrayOf(
            arrayOf(1, 0),
            arrayOf(2, 0),
            arrayOf(1, 1),
            arrayOf(2, 1),
            arrayOf(-1, 0),
            arrayOf(-2, 0),
            arrayOf(-1, 1),
            arrayOf(-2, 1),
            arrayOf(0, -1),
            arrayOf(3, 0),
            arrayOf(-3, 0)
        ), arrayOf(
            arrayOf(0, 1),
            arrayOf(0, 2),
            arrayOf(-1, 1),
            arrayOf(-1, 2),
            arrayOf(0, -1),
            arrayOf(0, -2),
            arrayOf(-1, -1),
            arrayOf(-1, -2),
            arrayOf(1, 0),
            arrayOf(0, 3),
            arrayOf(0, -3)
        ), arrayOf(
            arrayOf(-1, 0),
            arrayOf(-2, 0),
            arrayOf(-1, -1),
            arrayOf(-2, -1),
            arrayOf(1, 0),
            arrayOf(2, 0),
            arrayOf(1, -1),
            arrayOf(2, -1),
            arrayOf(0, 1),
            arrayOf(-3, 0),
            arrayOf(3, 0)
        ), arrayOf(
            arrayOf(0, 1),
            arrayOf(0, 2),
            arrayOf(1, 1),
            arrayOf(1, 2),
            arrayOf(0, -1),
            arrayOf(0, -2),
            arrayOf(1, -1),
            arrayOf(1, -2),
            arrayOf(-1, 0),
            arrayOf(0, 3),
            arrayOf(0, -3)
        )
    )
    val WALLKICK_I_180 = arrayOf(
        arrayOf(arrayOf(-1, 0), arrayOf(-2, 0), arrayOf(1, 0), arrayOf(2, 0), arrayOf(0, 1)),
        arrayOf(arrayOf(0, 1), arrayOf(0, 2), arrayOf(0, -1), arrayOf(0, -2), arrayOf(-1, 0)),
        arrayOf(arrayOf(1, 0), arrayOf(2, 0), arrayOf(-1, 0), arrayOf(-2, 0), arrayOf(0, -1)),
        arrayOf(arrayOf(0, 1), arrayOf(0, 2), arrayOf(0, -1), arrayOf(0, -2), arrayOf(1, 0))
    )

}
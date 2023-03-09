package dev.jombi.tetris.tetrimino.impl

import dev.jombi.tetris.block.Block
import dev.jombi.tetris.block.BlockColor
import dev.jombi.tetris.tetrimino.Tetrimino

class IMino : Tetrimino {
    override val blockWidth: Int
        get() = 4
    override val blockHeight: Int
        get() = 4
    override val blocks: Array<Array<Array<Block?>>> = arrayOf(
        arrayOf(
            arrayOfNulls(4),
            Array(4) { Block(it, 1, color) },
            arrayOfNulls(4),
            arrayOfNulls(4)
        ),
        arrayOf(
            arrayOf(null, null, Block(2, 0, color), null),
            arrayOf(null, null, Block(2, 1, color), null),
            arrayOf(null, null, Block(2, 2, color), null),
            arrayOf(null, null, Block(2, 3, color), null)
        ),
        arrayOf(
            arrayOfNulls(4),
            arrayOfNulls(4),
            Array(4) { Block(it, 2, color) },
            arrayOfNulls(4)
        ),
        arrayOf(
            arrayOf(null, Block(1, 0, color), null, null),
            arrayOf(null, Block(1, 1, color), null, null),
            arrayOf(null, Block(1, 2, color), null, null),
            arrayOf(null, Block(1, 3, color), null, null)
        )
    )
    override val color: BlockColor
        get() = BlockColor.CYAN
}
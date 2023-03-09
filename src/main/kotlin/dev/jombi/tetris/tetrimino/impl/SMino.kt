package dev.jombi.tetris.tetrimino.impl

import dev.jombi.tetris.block.Block
import dev.jombi.tetris.block.BlockColor
import dev.jombi.tetris.tetrimino.Tetrimino

class SMino : Tetrimino {
    override val blockWidth: Int
        get() = 3
    override val blockHeight: Int
        get() = 3
    override val blocks: Array<Array<Array<Block?>>> = arrayOf(
        arrayOf(
            arrayOf(null, Block(1, 0, color), Block(2, 0, color)),
            arrayOf(Block(0, 1, color), Block(1, 1, color), null),
            arrayOfNulls(3)
        ),
        arrayOf(
            arrayOf(null, Block(1, 0, color), null),
            arrayOf(null, Block(1, 1, color), Block(2, 1, color)),
            arrayOf(null, null, Block(2, 2, color))
        ),
        arrayOf(
            arrayOfNulls(3),
            arrayOf(null, Block(1, 1, color), Block(2, 1, color)),
            arrayOf(Block(0, 2, color), Block(1, 2, color), null)
        ),
        arrayOf(
            arrayOf(Block(0, 0, color), null, null),
            arrayOf(Block(0, 1, color), Block(1, 1, color), null),
            arrayOf(null, Block(1, 2, color), null)
        )
    )

    override val color: BlockColor
        get() = BlockColor.GREEN

}
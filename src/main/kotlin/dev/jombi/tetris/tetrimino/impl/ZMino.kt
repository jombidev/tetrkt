package dev.jombi.tetris.tetrimino.impl

import dev.jombi.tetris.block.Block
import dev.jombi.tetris.block.BlockColor
import dev.jombi.tetris.tetrimino.Tetrimino

class ZMino : Tetrimino {
    override val blockWidth: Int
        get() = 3
    override val blockHeight: Int
        get() = 3
    override val blocks: Array<Array<Array<Block?>>> = arrayOf(
        arrayOf(
            arrayOf(Block(0, 0, color), Block(1, 0, color), null),
            arrayOf(null, Block(1, 1, color), Block(2, 1, color)),
            arrayOfNulls(3)
        ),
        arrayOf(
            arrayOf(null, null, Block(2, 0, color)),
            arrayOf(null, Block(1, 1, color), Block(2, 1, color)),
            arrayOf(null, Block(1, 2, color), null)
        ),
        arrayOf(
            arrayOfNulls(3),
            arrayOf(Block(0, 1, color), Block(1, 1, color), null),
            arrayOf(null, Block(1, 2, color), Block(2, 2, color)),
        ),
        arrayOf(
            arrayOf(null, Block(1, 0, color), null),
            arrayOf(Block(0, 1, color), Block(1, 1, color), null),
            arrayOf(Block(0, 2, color), null, null)
        )
    )
    override val color: BlockColor
        get() = BlockColor.RED
}
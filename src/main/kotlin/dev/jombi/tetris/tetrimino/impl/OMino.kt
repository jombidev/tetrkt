package dev.jombi.tetris.tetrimino.impl

import dev.jombi.tetris.block.Block
import dev.jombi.tetris.block.BlockColor
import dev.jombi.tetris.tetrimino.Tetrimino

class OMino : Tetrimino {
    override val blockWidth: Int
        get() = 4
    override val blockHeight: Int
        get() = 3
    override val blocks: Array<Array<Array<Block?>>> = Array(4) {
        arrayOf(
            arrayOf(null, Block(1, 0, color), Block(2, 0, color), null),
            arrayOf(null, Block(1, 1, color), Block(2, 1, color), null),
            arrayOfNulls(4)
        )
    }

    override val color: BlockColor
        get() = BlockColor.YELLOW
}
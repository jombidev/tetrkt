package dev.jombi.tetris.tetrimino

import dev.jombi.tetris.block.Block
import dev.jombi.tetris.block.BlockColor

interface Tetrimino {
    val blocks: Array<Array<Array<Block?>>>
    val color: BlockColor
    val blockWidth: Int
    val blockHeight: Int
}
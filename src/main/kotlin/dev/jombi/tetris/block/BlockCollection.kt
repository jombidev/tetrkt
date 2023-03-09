package dev.jombi.tetris.block

class BlockCollection(val x: Int, val y: Int) : Iterable<Block?> {
    private val array = Array<Block?>(x * y) { null }
    fun contains(element: Block): Boolean {
        return array.contains(element)
    }

    val size: Int
        get() = array.filterNotNull().size

    fun isEmpty(): Boolean {
        return array.filterNotNull().isEmpty()
    }

    override fun iterator(): Iterator<Block?> {
        return array.iterator()
    }
    fun clearField() {
        for (i in array.indices) {
            array[i] = null
        }
    }

    fun place(xx: Int, yy: Int, color: BlockColor) {
        array[yy * x + xx] = Block(xx, yy, color)
    }
}
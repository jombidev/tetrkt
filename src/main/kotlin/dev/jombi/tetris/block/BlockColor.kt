package dev.jombi.tetris.block

enum class BlockColor(val colorCode: Int) {
    CYAN(0xff00ffff.toInt()), BLUE(0xff0000ff.toInt()), ORANGE(0xffffA500.toInt()),
    YELLOW(0xffffff00.toInt()), GREEN(0xff00ff00.toInt()), PURPLE(0xffa020f0.toInt()),
    RED(0xffff0000.toInt()), GRAY(0xff646464.toInt())
}
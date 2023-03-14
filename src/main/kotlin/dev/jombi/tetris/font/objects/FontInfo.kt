package dev.jombi.tetris.font.objects

import org.lwjgl.stb.STBTTFontinfo
import java.nio.ByteBuffer

data class FontInfo(val data: ByteBuffer, val info: STBTTFontinfo, val ascent: Int, val descent: Int, val lineGap: Int)
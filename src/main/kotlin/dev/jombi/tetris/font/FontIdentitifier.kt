package dev.jombi.tetris.font

import dev.jombi.tetris.font.objects.FontInfo
import org.lwjgl.BufferUtils
import org.lwjgl.stb.STBTTFontinfo
import org.lwjgl.stb.STBTruetype
import org.lwjgl.system.MemoryStack
import org.lwjgl.system.MemoryUtil
import java.nio.ByteBuffer
import java.nio.channels.Channels
import kotlin.system.exitProcess

private fun fetchFont(direction: String, bufSize: Int = 512 * 1024): ByteBuffer {
    return try {
        val buf = Channels.newChannel(ClassLoader.getSystemResourceAsStream("fonts/$direction") ?: throw Exception("Font not found."))
        var buffer = BufferUtils.createByteBuffer(bufSize)
        while (true) {
            val bytes = buf.read(buffer)
            if (bytes == -1) break
            if (buffer.remaining() == 0) buffer = resizeBuffer(buffer, buffer.capacity() * 3 / 2)
        }
        buffer.flip()
        MemoryUtil.memSlice(buffer)
    } catch (e: Exception) {
        println("Font not found: $direction")
        exitProcess(-1)
    }
}

private fun resizeBuffer(buffer: ByteBuffer, newCapacity: Int): ByteBuffer {
    val newBuffer = BufferUtils.createByteBuffer(newCapacity)
    buffer.flip()
    newBuffer.put(buffer)
    return newBuffer
}

private fun ByteBuffer.initFont(): FontInfo {
    val info = STBTTFontinfo.create()
    if (!STBTruetype.stbtt_InitFont(info, this)) throw IllegalStateException("Failed to initialize Font information.")
    val ascent: Int
    val descent: Int
    val lineGap: Int
    MemoryStack.stackPush().use {
        val pAscent = it.mallocInt(1)
        val pDescent = it.mallocInt(1)
        val pLineGap = it.mallocInt(1)
        STBTruetype.stbtt_GetFontVMetrics(info, pAscent, pDescent, pLineGap)
        ascent = pAscent.get(0)
        descent = pDescent.get(0)
        lineGap = pLineGap.get(0)
    }
    return FontInfo(this, info, ascent, descent, lineGap)
}

enum class FontType(val font: FontInfo) {
    TAHOMA(fetchFont("tahoma.ttf").initFont()),
    ARIAL(fetchFont("arial.ttf").initFont())
}
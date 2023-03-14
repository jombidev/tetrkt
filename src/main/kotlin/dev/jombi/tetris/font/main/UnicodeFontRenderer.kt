package dev.jombi.tetris.font.main

import dev.jombi.tetris.Drawer
import dev.jombi.tetris.font.objects.FontInfo
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL11.*
import org.lwjgl.stb.*
import org.lwjgl.system.MemoryUtil

@Suppress("PrivatePropertyName")
class UnicodeFontRenderer constructor(private val font: FontInfo, private val fontSize: Float) {
    private val BITMAP_WIDTH = 1024
    private val BITMAP_HEIGHT = 1024

    val FONT_HEIGHT: Float

    private val cdata: STBTTPackedchar.Buffer = STBTTPackedchar.malloc(6 * 128)
    private val tex: Int = glGenTextures()

    init {
        STBTTPackContext.malloc().use {
            val ttf = font.data
            val bitmap = BufferUtils.createByteBuffer(BITMAP_WIDTH * BITMAP_HEIGHT)

            STBTruetype.stbtt_PackBegin(it, bitmap, BITMAP_WIDTH, BITMAP_HEIGHT, 0, 1, MemoryUtil.NULL)

            var p = 32
            cdata.limit(p + 95)
            cdata.position(p)
            STBTruetype.stbtt_PackSetOversampling(it, 1, 1)
            STBTruetype.stbtt_PackFontRange(it, ttf, 0, fontSize, 32, cdata)

            p += 128
            cdata.limit(p + 95)
            cdata.position(p)
            STBTruetype.stbtt_PackSetOversampling(it, 2, 2)
            STBTruetype.stbtt_PackFontRange(it, ttf, 0, fontSize, 32, cdata)

            p += 128
            cdata.limit(p + 95)
            cdata.position(p)
            STBTruetype.stbtt_PackSetOversampling(it, 3, 1)
            STBTruetype.stbtt_PackFontRange(it, ttf, 0, fontSize, 32, cdata)

            cdata.clear()
            STBTruetype.stbtt_PackEnd(it)

            glBindTexture(GL_TEXTURE_2D, tex)
            glTexImage2D(GL_TEXTURE_2D, 0, GL_ALPHA, BITMAP_WIDTH, BITMAP_HEIGHT, 0, GL_ALPHA, GL_UNSIGNED_BYTE, bitmap)
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR)
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR)
        }
        val chars = ('a'..'z') + ('A'..'Z') + ('0'..'9')

        var height = 0.0f
        val q: STBTTAlignedQuad = STBTTAlignedQuad.malloc()
        for (c in chars) {
            STBTruetype.stbtt_GetPackedQuad(cdata, BITMAP_WIDTH, BITMAP_HEIGHT, c.code, floatArrayOf(0f), floatArrayOf(0f), q, false)
            height = maxOf(height, q.y1() - q.y0())
        }
        FONT_HEIGHT = height
    }

    private val q: STBTTAlignedQuad = STBTTAlignedQuad.malloc()
    private val xb = MemoryUtil.memAllocFloat(1)
    private val yb = MemoryUtil.memAllocFloat(1)

    private val qWidth: STBTTAlignedQuad = STBTTAlignedQuad.malloc()

    private fun renderString(text: String, x: Number, y: Number, color: Int, shadow: Boolean) {
        xb.put(0, x.toFloat())
        yb.put(0, y.toFloat())

        cdata.position(128)
        glEnable(GL_BLEND)
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA)

        val multiplier = 255.0f * if (shadow) 4 else 1


        val (red, green, blue, alpha) = Drawer.fetchColor(color)
        glColor4f(red * 255f / multiplier, green * 255f / multiplier, blue * 255f / multiplier, alpha)

        glEnable(GL_TEXTURE_2D)
        glBindTexture(GL_TEXTURE_2D, tex)
        glBegin(GL_QUADS)
        for (h in text) {
            STBTruetype.stbtt_GetPackedQuad(cdata, BITMAP_WIDTH, BITMAP_HEIGHT, h.code, xb, yb, q, false)
            glTexCoord2f(q.s0(), q.t0()); glVertex2f(q.x0(), q.y0())
            glTexCoord2f(q.s1(), q.t0()); glVertex2f(q.x1(), q.y0())
            glTexCoord2f(q.s1(), q.t1()); glVertex2f(q.x1(), q.y1())
            glTexCoord2f(q.s0(), q.t1()); glVertex2f(q.x0(), q.y1())
        }
        glEnd()
        glBindTexture(GL_TEXTURE_2D, 0)
        glDisable(GL_TEXTURE_2D)
        glDisable(GL_BLEND)
    }

    fun drawString(text: String, x: Number, y: Number, color: Int) {
        renderString(text, x, y, color, false)
    }

    fun drawStringWithShadow(text: String, x: Number, y: Number, color: Int) {
        glTranslated(0.5, 0.5, 0.0)
        renderString(text, x, y, color, true)
        glTranslated(-0.5, -0.5, 0.0)
        renderString(text, x, y, color, false)
    }

    fun getStringWidth(text: String): Float {
        return getStringBound(text)[0]
    }

    fun getStringBound(text: String): FloatArray {
        var width = 0.0f
        var height = 0.0f
        for (c in text) {
            STBTruetype.stbtt_GetPackedQuad(cdata, BITMAP_WIDTH, BITMAP_HEIGHT, c.code, floatArrayOf(0f), floatArrayOf(0f), qWidth, false)
            val w = qWidth.x1() - qWidth.x0()
            width += w
            height = maxOf(height, qWidth.y1() - qWidth.y0())
        }
        return floatArrayOf(width, height)
    }
}
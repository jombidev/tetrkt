package dev.jombi.tetris.font

import dev.jombi.tetris.font.main.UnicodeFontRenderer
import java.util.EnumMap

object FontFactory {
    private val FONT_ARRAY = EnumMap<FontType, Array<UnicodeFontRenderer?>>(FontType::class.java)
    fun getFont(type: FontType): UnicodeFontRenderer {
        return getFont(type, 16)
    }

    fun getFont(type: FontType, size: Int): UnicodeFontRenderer {
        if (!FONT_ARRAY.containsKey(type)) {
            FONT_ARRAY[type] = arrayOfNulls(256)
        }
        val array: Array<UnicodeFontRenderer?> = FONT_ARRAY[type]!!
        if (array[size] == null) {
            array[size] = UnicodeFontRenderer(type.font, size.toFloat())
        }
        return array[size]!!
    }
}
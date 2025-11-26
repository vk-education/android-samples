package company.vk.lesson07.businesslayer.providers

import android.graphics.Color

class ColorProvider {
    suspend fun generateColor(): Int {
        return COLORS.random()
    }

    companion object {
        protected val COLORS = listOf(
            Color.BLUE,
            Color.CYAN,
            Color.RED,
            Color.YELLOW,
            Color.WHITE,
            Color.MAGENTA,
            Color.GREEN,
            Color.BLACK
        )
    }
}

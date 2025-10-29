package com.example.compose_sample.compositionlocal.ds

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color

val LocalColorScheme = compositionLocalOf { AppLightColorScheme }

val AppLightColorScheme = ColorsScheme(
    text = ColorsScheme.Text(
        primary = Color(0, 0, 0, 0xFF),
        secondary = Color(11, 11, 11, 0xFF)
    ),
    icon = ColorsScheme.Icon(
        primary = Color(57, 83, 255, 255),
        secondary = Color(0x28, 0x28, 0x28, 0xFF)
    ),
    background = ColorsScheme.Background(
        content = Color(0xFF, 0xFF, 0xFF, 0xFF)
    ),
)

val AppDarkColorScheme = ColorsScheme(
    text = ColorsScheme.Text(
        primary = Color(0xFF, 0xFF, 0xFF, 0xFF),
        secondary = Color(0xDE, 0xDE, 0xDE, 0xFF)
    ),
    icon = ColorsScheme.Icon(
        primary = Color(110, 129, 255, 255),
        secondary = Color(0xC7, 0xC7, 0xC7, 0xFF)
    ),
    background = ColorsScheme.Background(
        content = Color(0, 0, 0, 0xFF)
    ),
)

data class ColorsScheme(
    val text: Text,
    val icon: Icon,
    val background: Background,
) {
    data class Text(
        val primary: Color,
        val secondary: Color,
    )

    data class Icon(
        val primary: Color,
        val secondary: Color,
    )

    data class Background(
        val content: Color,
    )
}

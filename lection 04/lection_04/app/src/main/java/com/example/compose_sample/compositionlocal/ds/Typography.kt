package com.example.compose_sample.compositionlocal.ds

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val LocalTypography = staticCompositionLocalOf { AppTypography }

private val AppTypography = Typography(
    display = TextStyle(
        fontSize = 24.sp,
        lineHeight = 28.sp,
    ),
    title = TextStyle(
        fontSize = 16.sp,
        lineHeight = 20.sp,
    ),
    titleMedium = TextStyle(
        fontSize = 16.sp,
        lineHeight = 20.sp,
        fontWeight = FontWeight.W500,
    ),
    label = TextStyle(
        fontSize = 14.sp,
        lineHeight = 16.sp,
    ),
)

data class Typography(
    val display: TextStyle,
    val title: TextStyle,
    val titleMedium: TextStyle,
    val label: TextStyle,
)

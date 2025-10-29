@file:Suppress("unused")

package com.example.compose_sample.recomposition

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@Composable
fun SomeDisappearingText(text: String, startColor: Color) {
    val gradientBrush = remember(startColor) {
        Brush.linearGradient(
            colors = listOf(
                startColor,
                Color.Transparent
            )
        )
    }

    Text(
        text = text,
        modifier = Modifier
            .drawWithCache {
                onDrawWithContent {
                    drawContent()
                    drawRect(
                        brush = gradientBrush,
                        blendMode = BlendMode.SrcAtop,
                    )
                }
            }
    )
}

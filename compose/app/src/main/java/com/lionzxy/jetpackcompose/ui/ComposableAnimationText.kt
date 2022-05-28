package com.lionzxy.jetpackcompose.ui

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.sp
import kotlin.math.roundToInt

@Composable
fun ComposableAnimationText(originalText: String) {
    Text(
        text = originalText + animatedDots(),
        fontSize = 80.sp
    )
}

@Composable
private fun animatedDots(): String {
    val infiniteTransition = rememberInfiniteTransition()
    val currentDotsCount by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 3f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )
    return ".".repeat(currentDotsCount.roundToInt())
}

package com.example.compose_sample.modifier

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Preview(showBackground = true, widthDp = 200, heightDp = 200)
@Composable
fun AlphaSample() {
    Box(
        modifier = Modifier
            .alpha(0.5f)
            .background(Color.Red)
    )
}

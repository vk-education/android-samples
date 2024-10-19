package com.example.compose_sample.modifier

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showSystemUi = true)
@Composable
fun ModifierOrder1Sample() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Red)
            .alpha(0.5f)
            .clip(CircleShape),
    )
}

@Preview(showSystemUi = true)
@Composable
fun ModifierOrder2PaddingSample() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 16.dp)
            .background(Color.Red)
            .padding(16.dp)
            .background(Color.Blue),
    )
}

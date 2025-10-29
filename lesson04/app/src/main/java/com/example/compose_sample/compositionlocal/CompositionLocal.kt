package com.example.compose_sample.compositionlocal

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

// Code style: CompositionLocal should have prefix `Local`
val LocalTextColor = compositionLocalOf { Color.Blue }

@Preview
@Composable
fun Test() {
    Text(
        text = "Text",
        color = LocalTextColor.current,
    )
}

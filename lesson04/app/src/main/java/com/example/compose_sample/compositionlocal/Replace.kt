package com.example.compose_sample.compositionlocal

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun CustomTextColor() {
    CompositionLocalProvider(
        LocalTextColor provides Color.Red
    ) {
        Text(
            text = "Red text",
            color = LocalTextColor.current,
        )
    }
}

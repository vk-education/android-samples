package com.example.compose_sample.animation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun VisibleAnimation() {
    var isTextVisible by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AnimatedVisibility(
            visible = isTextVisible,
        ) {
            Text(text = "Some text")
        }
        Button(
            onClick = {
                isTextVisible = !isTextVisible
            },
        ) {
            Text(
                text = if (isTextVisible) {
                    "Hide"
                } else {
                    "Show"
                },
            )
        }
    }
}

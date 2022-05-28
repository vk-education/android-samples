package com.lionzxy.jetpackcompose.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Composable
fun ComposableHelloWorld(
    name: String
) {
    Row() {
        Text(
            modifier = Modifier
                .weight(1f),
            text = "Hello, $name!",
            fontSize = 60.sp
        )
        Text(
            modifier = Modifier
                .weight(1f),
            text = "Bye, $name!",
            fontSize = 60.sp
        )
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
private fun ComposableHelloWorldPreview() {
    ComposableHelloWorld("Preview")
}

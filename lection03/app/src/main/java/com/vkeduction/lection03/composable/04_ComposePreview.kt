package com.vkeduction.lection03.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun HelloWorld() {
    Box() {
        Text("Hello World")
    }
}

@Preview(
    showSystemUi = true,
    showBackground = true
)
@Composable
private fun HelloWorldPreview() {
    HelloWorld()
}
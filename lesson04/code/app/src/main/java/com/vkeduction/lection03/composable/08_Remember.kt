package com.vkeduction.lection03.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Preview(
    showSystemUi = true,
    showBackground = true
)
@Composable
fun Remember() {
    var counter by remember { mutableStateOf(0) }
    val timestamp = remember {
        System.currentTimeMillis()
    }
    val timestampDynamic = System.currentTimeMillis()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = counter.toString())

        Text(
            modifier = Modifier.clickable { counter++ },
            text = "Click on Me!"
        )
        Text("Timestamp: $timestamp")
        Text("Timestamp2: $timestampDynamic")
    }
}
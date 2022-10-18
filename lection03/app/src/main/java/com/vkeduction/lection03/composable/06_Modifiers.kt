package com.vkeduction.lection03.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ModifiersComposable() {
    Box(
        modifier = Modifier
            .size(60.dp)
            .background(Color.Red)
    )
}

@Composable
fun ModifiersCombineComposable() {
    val modifier = Modifier.size(20.dp)

    Box(
        modifier = modifier
            .background(Color.Red)
    )
}

@Composable
fun ModifiersThenComposable() {
    val modifier = Modifier.size(20.dp)

    Box(
        modifier = Modifier
            .background(Color.Gray)
            .then(modifier)
    )
}
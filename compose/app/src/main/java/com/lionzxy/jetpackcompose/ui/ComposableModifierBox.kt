package com.lionzxy.jetpackcompose.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun ComposableModifierBox() = Box {
    Box(
        modifier = Modifier
            .size(300.dp)
            .background(Color.Red)
            .clickable(
                indication = rememberRipple(),
                interactionSource = MutableInteractionSource(),
                onClick = {}
            )
            .padding(16.dp)
            .background(Color.Cyan)
            .padding(16.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(Color.Gray)
            .offset(100.dp)
            .background(Color.Black)

    )
}

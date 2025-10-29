package com.example.compose_sample.modifier

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showBackground = true, widthDp = 200, heightDp = 200)
@Composable
fun ClipSample() {
    Box(
        modifier = Modifier
            .padding(all = 16.dp)
            .clip(
                RoundedCornerShape(
                    topStart = 24.dp,
                    topEnd = 24.dp,
                    bottomStart = 24.dp,
                    bottomEnd = 24.dp,
                )
            )
            .background(Color.Red),
    )
}

@Preview(showBackground = true, widthDp = 200, heightDp = 200)
@Composable
fun CircleSample() {
    Box(
        modifier = Modifier
            .padding(all = 16.dp)
            .clip(CircleShape)
            .background(Color.Red),
    )
}

@Preview(showBackground = true, widthDp = 200, heightDp = 200)
@Composable
fun ShapeInBackgroundSample() {
    Box(
        modifier = Modifier
            .padding(all = 16.dp)
            .background(
                color = Color.Red,
                shape = CircleShape,
            )
            .clickable {
                // do something
            }
    )
}

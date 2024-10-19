package com.example.compose_sample.modifier

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showSystemUi = true)
@Composable
fun WrapContentSample() {
    Box(
        modifier = Modifier
            .wrapContentSize()
            .background(Color.Red),
    ) {
        Text(
            text = "Content",
            modifier = Modifier
                .padding(all = 16.dp),
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun FillMaxSizeSample() {
    Box(
        modifier = Modifier
            .fillMaxSize(
                // fraction = 0.5f,
            )
            .background(Color.Red),
    ) {
        Text(
            text = "Content",
            modifier = Modifier
                .padding(all = 16.dp),
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun FillMaxWidthSample() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Red),
    ) {
        Text(
            text = "Content",
            modifier = Modifier
                .padding(all = 16.dp),
        )
    }
}

package com.example.compose_sample.layout

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun BoxSample() {
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Text(
            text = "Text 1",
            modifier = Modifier.align(Alignment.Center),
        )
        Button(
            onClick = {},
            modifier = Modifier.align(Alignment.BottomEnd),
        ) {
            Text(
                text = "+",
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun ColumnSample() {
    Column(verticalArrangement = Arrangement.Center) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = "Text",
            )
        }
        Row {
            MenuItem(
                title = "First",
                modifier = Modifier.weight(1f),
            )
            MenuItem(
                title = "Second",
                modifier = Modifier.weight(1f),
            )
            MenuItem(
                title = "Third",
                modifier = Modifier.weight(1f),
            )
        }
    }
}

@Composable
private fun MenuItem(
    title: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        Image(
            painter = ColorPainter(Color.LightGray),
            contentDescription = null,
            modifier = Modifier
                .size(24.dp),
        )
        Text(
            text = title,
            textAlign = TextAlign.Center,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MenuItemPreview() {
    MenuItem(title = "Title")
}

@Preview(showSystemUi = true)
@Composable
private fun BoxSamplePreview() {
    BoxSample()
}

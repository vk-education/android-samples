package com.example.compose_sample.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout

@OptIn(ExperimentalLayoutApi::class)
@Preview(showBackground = true)
@Composable
fun FlowRowSample() {
    val chips = remember { listOf("First", "Second", "Third", "Fourth", "Fifth", "Sixth") }
    var selectedChips by remember { mutableStateOf(listOf("Second")) }

    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.padding(
            horizontal = 16.dp,
        ),
    ) {
        chips.forEach { chip ->
            FilterChip(
                selected = chip in selectedChips,
                label = {
                    Text(text = chip)
                },
                onClick = {
                    selectedChips = if (chip in selectedChips) {
                        selectedChips - chip
                    } else {
                        selectedChips + chip
                    }
                },
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Preview(showBackground = true)
@Composable
fun FlowColumnSample() {
    val cells = remember { listOf("First", "Second", "Third", "Fourth", "Fifth", "Sixth") }

    FlowColumn(
        modifier = Modifier.height(200.dp),
    ) {
        cells.forEach { cell ->
            Text(
                text = cell,
                modifier = Modifier.padding(all = 16.dp),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
// Compose constraint layout dependency is required
fun ConstraintLayoutSample() {
    ConstraintLayout(modifier = Modifier.size(300.dp)) {
        val (image, text, button) = createRefs()
        Box(
            modifier = Modifier
                .constrainAs(image) {
                    top.linkTo(
                        anchor = parent.top,
                        margin = 8.dp,
                    )
                    start.linkTo(
                        anchor = parent.start,
                        margin = 16.dp,
                    )
                }
                .size(64.dp)
                .background(
                    color = MaterialTheme.colorScheme.tertiary,
                    shape = CircleShape,
                ),
        )
        Text(
            text = "Hello, world!",
            modifier = Modifier.constrainAs(text) {
                top.linkTo(
                    anchor = image.top,
                )
                start.linkTo(
                    anchor = image.end,
                    margin = 12.dp,
                )
            },
            style = MaterialTheme.typography.titleLarge
        )
        Button(
            onClick = {},
            modifier = Modifier
                .constrainAs(button) {
                    top.linkTo(
                        anchor = text.bottom,
                        margin = 4.dp,
                    )
                    start.linkTo(
                        anchor = text.start,
                    )
                }
        ) {
            Text(text = "Do something")
        }
    }
}

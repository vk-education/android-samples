package com.example.compose_sample.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.toPersistentList

@Preview(showBackground = true)
@Composable
fun ColumnListSample() {
    val items = remember { listOf("First", "Second", "Third", "Fourth") }

    Column {
        items.forEach { item ->
            Text(
                text = item,
                modifier = Modifier
                    .padding(all = 16.dp),
            )
        }
    }
}

@Preview(showBackground = true, heightDp = 300)
@Composable
fun LazyColumnListSample() {
    val items = remember { List(100) { "Text $it" } }

    LazyColumn {
        items(
            items = items,
            key = null,
            contentType = { null },
        ) { item ->
            Text(
                text = item,
                modifier = Modifier
                    .padding(all = 16.dp),
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun LazyVerticalGridSample() {
    var items by remember {
        mutableStateOf(
            List(100_000) { "Item #$it" }
                // У persistent list есть удобные методы для создания новых
                // списков на основе существующего: add, remove и тому подобные
                .toPersistentList()
        )
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
    ) {
        items(items = items) {
            Text(
                text = it,
                modifier = Modifier
                    .padding(all = 8.dp)
                    .background(Color.Red)
                    .padding(all = 8.dp),
            )
        }
    }
}

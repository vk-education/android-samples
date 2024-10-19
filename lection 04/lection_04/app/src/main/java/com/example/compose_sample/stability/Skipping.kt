@file:Suppress("unused")

package com.example.compose_sample.stability

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

// non skipping composable function
@Composable
fun ContactsList(
    contacts: List<String>,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier,
    ) {
        items(
            items = contacts,
        ) {
            Text(text = it)
        }
    }
}

package com.example.compose_sample.animation

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

sealed interface SomeScreenState {
    data object Loading : SomeScreenState

    @Immutable
    data class Content(
        val items: List<String>
    ) : SomeScreenState
}

@Preview
@Composable
fun SomeScreen() {
    var state by remember { mutableStateOf<SomeScreenState>(SomeScreenState.Loading) }

    Crossfade(
        targetState = state,
        label = "SomeScreenCrossfade",
    ) {
        when(it) {
            is SomeScreenState.Loading -> {
                Loading()
            }

            is SomeScreenState.Content -> {
                Content(it)
            }
        }
    }

    LaunchedEffect(Unit) {
        delay(1500)
        state = SomeScreenState.Content(
            listOf("Item 1", "Item 2", "Item 3", "Item 4")
        )
    }
}

@Composable
private fun Loading() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun Content(content: SomeScreenState.Content) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        items(content.items) {
            Text(
                text = it,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 16.dp),
            )
        }
    }
}

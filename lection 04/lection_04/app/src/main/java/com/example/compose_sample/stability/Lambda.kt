@file:Suppress("UNUSED_PARAMETER", "unused")

package com.example.compose_sample.stability

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

sealed interface Action {
    data object LoadImage : Action
}

class HomeViewModel : ViewModel() {
    // flows, work with state, etc

    fun onAction(action: Action) {
        // do something on ui action
    }
}

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel(),
) {
    Button(
        onClick = remember {
            {
                viewModel.onAction(Action.LoadImage)
            }
        }
    ) {
        Text(text = "Load image")
    }
}

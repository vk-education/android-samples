package com.vkeduction.lection03.composable

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vkeduction.lection03.viewmodel.TestViewModel

@Composable
fun MVVMComposable() {
    val testViewModel = viewModel<TestViewModel>()
    val input by testViewModel.stateFlow.collectAsState()
    Text("Hello, $input")
}
package com.lionzxy.jetpackcompose.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lionzxy.jetpackcompose.R
import com.lionzxy.jetpackcompose.viewmodel.CounterViewModel

@Composable
fun ComposableCounterViewModel(counterViewModel: CounterViewModel) {
    val number by counterViewModel.getNumberState().observeAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = number.toString(),
            fontSize = 48.sp
        )
        TextButton(
            onClick = counterViewModel::increment
        ) {
            Text(
                text = stringResource(R.string.clicker_btn_text)
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ComposableCounterViewModelPreview() {
    ComposableCounterViewModel(viewModel())
}

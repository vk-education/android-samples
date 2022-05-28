package com.lionzxy.jetpackcompose.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.lionzxy.jetpackcompose.R

@Composable
fun ComposableCounter() {
    var number by remember { mutableStateOf(0) }

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
            onClick = { number++ }
        ) {
            Text(
                text = stringResource(R.string.clicker_btn_text)
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ComposableCounterPreview() {
    ComposableCounter()
}

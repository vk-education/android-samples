@file:Suppress("unused", "UNUSED_PARAMETER")

package com.example.compose_sample.stability

import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State

// --- 1 ---
// Будет ли функция SomeText пропускаемой?

@Composable
fun SomeText(text: State<String>) {
    Text(text = text.value)
}

// Да, androidx.compose.runtime.State считается стабильным типом,
// поскольку предупреждает Compose об изменениях объекта, который он
// держит внутри себя.
// Google не рекомендует передавать State между Composable-функциями.

// --- 2 ---
// Будет ли функция SomeEditableText пропускаемой?

@Composable
fun SomeEditableText(text: MutableState<String>) {
    TextField(
        value = text.value,
        onValueChange = {
            text.value = it
        },
    )
}

// Да, будет, поскольку MutableState также считается стабильным
// и предупреждает Compose об изменениях объекта, который
// он держит внутри себя.
// !!! Google не рекомендует передавать State между Composable-функциями,
// а тем более и MutableState.

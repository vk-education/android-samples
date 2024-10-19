package com.example.compose_sample.side

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.tooling.preview.Preview

var globalCounter = 1123

// Очень сомнительный пример, поскольку не встречается в реальном коде
@Preview
@Composable
fun DoSomething() {
    // Поведение с сайд эффектом и без него будет разное
    SideEffect {
        globalCounter++
    }

    Text("$globalCounter")
}

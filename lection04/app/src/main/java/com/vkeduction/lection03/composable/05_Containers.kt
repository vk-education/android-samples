package com.vkeduction.lection03.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun WithoutBoxComposable() {
    Text("Первый текст")
    Text("Второй текст")
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun BoxComposable() {
    Box {
        Text("Первый текст")
        Text("Второй текст")
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun BoxSecondComposable() {
    Box(contentAlignment = Alignment.Center) {
        Text("Первый текст")
        Text("Второй текст")
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun RowComposable() {
    Row {
        Text("Первый текст")
        Text("Второй текст")
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun ColumnComposable() {
    Column {
        Text("Первый текст")
        Text("Второй текст")
    }
}

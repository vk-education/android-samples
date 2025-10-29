@file:Suppress("unused")
@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.compose_sample.side

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

// Используем key
@Composable
fun ListItem(
    // На onViewed можно, например, отправлять событие аналитики
    onViewed: () -> Unit,
) {
    Text(
        text = "Some Item",
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 16.dp),
    )

    // Т.к. передан ключ Unit, onViewed вызовется строго один раз
    // и не будет вызываться на рекомпозицию
    LaunchedEffect(key1 = Unit) {
        onViewed()
    }
}

// Используем key + вызываем suspend-функции
@Composable
fun ScreenWithBottomSheet(
    isSheetExpanded: Boolean,
) {
    val state = rememberBottomSheetScaffoldState()

    BottomSheetScaffold(
        sheetContent = {
            SheetContent()
        },
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            Text(text = "Screen content")
        }
    }

    LaunchedEffect(
        // Вызываем анимацию только если изменился параметр isSheetExpanded.
        key1 = isSheetExpanded,
    ) {
        // expand, hide – suspend функции, поскольку внутри они плавно меняют стейт оффсет,
        // отступ bottom sheet-а от начала экрана. За счет этого мы видим анимацию.
        if (isSheetExpanded) {
            state.bottomSheetState.expand()
        } else {
            state.bottomSheetState.hide()
        }
    }
}

@Composable
private fun SheetContent() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(text = "Sheet content")
    }
}

@Composable
private fun ScreenContent() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(text = "Screen content")
    }
}

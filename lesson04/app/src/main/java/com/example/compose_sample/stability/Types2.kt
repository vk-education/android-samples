@file:Suppress("unused", "UNUSED_PARAMETER")

package com.example.compose_sample.stability

import androidx.compose.runtime.Composable

// --- 1 ---
// Стабильный ли ScreenState?
// Будет ли функция Screen пропускаемой?

interface ScreenState

@Composable
fun Screen(state: ScreenState) {
    // Content
}

// Неизвестно, стабильность будет проверяться в рантайме.
// Функция будет пропускаемой.

// ---------

// --- 2 ---
// Стабильный ли Item?
// Будет ли функция ListItem пропускаемой?

abstract class Item {
    var price: Int = 0
}

@Composable
fun ListItem(item: Item) {
    // Content
}

// Нет, из-за var.
// Функция непропускаемая.

// ---------

// --- 3 ---
// Будет ли функция Contacts пропускаемой?

@Composable
fun Contacts(contacts: List<String>) {
    // Content
}

// Нет, из-за List.

// ---------

// --- 4 ---
// Будет ли тип данных стабильным?

data class FormItem<T>(
    val type: String,
    val content: T
)

// Неизвестно, стабильность будет определяться в рантайме.

// ---------

// --- 5 ---
// Будет ли тип данных стабильным?

data class Field<T : Any>(
    val type: String,
    val content: T
)

// Неизвестно, стабильность будет определяться в рантайме.
// `T : Any` говорит лишь о том, что T – non nullable тип.

// ---------

@file:Suppress("UNUSED_PARAMETER", "unused")

package com.example.compose_sample.stability

import androidx.compose.runtime.Composable

// Нестабильный тип данных
class UserDetails(
    var name: String
)

// Пропускаемая функция
@Composable
fun UserSettings(
    userDetails: UserDetails = UserDetails("Ivan")
) {
    // Content
}

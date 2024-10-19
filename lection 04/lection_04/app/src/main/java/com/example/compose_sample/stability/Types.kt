@file:Suppress("unused")

package com.example.compose_sample.stability

// Стабильный тип данных.
data class User(
    val name: String,
    val surname: String,
)

// Нестабильный тип данных из-за var.
class UserProfile(
    var name: String,
    var surname: String,
)

// Нестабильный тип данных, поскольку List нестабилен.
data class ShoppingCart(
    val products: List<Product>,
) {
    // Стабильный тип данных
    data class Product(
        val name: String,
        val price: Int,
    )
}

// Стабильный тип данных
data class Price(
    val currency: Currency,
    val value: Int,
    val formatted: String,
) {
    // enum стабилен всегда
    enum class Currency {
        Rub,
        Usd,
    }
}

// Нестабильный тип данных, поскольку Number нестабилен
data class SomeType(
    val i: Int,
    val n: Number,
)

// Нестабильный тип данных, поскольку Any нестабилен
data class ListItem(
    val key: Any,
    val title: String,
)

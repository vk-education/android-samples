package com.example.lecture06.domain.models

data class Cases(
    val active: Int,
    val critical: Int,
    val new: String,
    val recovered: Int,
    val total: Int,
)
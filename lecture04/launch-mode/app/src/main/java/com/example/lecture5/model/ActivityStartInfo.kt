package com.example.lecture5.model

import com.example.lecture5.presentation.activities.variants.A
import com.example.lecture5.presentation.activities.variants.B
import com.example.lecture5.presentation.activities.variants.C
import com.example.lecture5.presentation.activities.variants.D

class ActivityStartInfo(
    val description: String,
    val classA: Class<out A>,
    val classB: Class<out B>,
    val classC: Class<out C>,
    val classD: Class<out D>,
)
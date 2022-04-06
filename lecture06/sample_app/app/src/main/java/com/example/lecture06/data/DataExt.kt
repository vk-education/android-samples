package com.example.lecture06.data

import java.text.SimpleDateFormat
import java.util.*

fun Date.toFormat(format: String = "yyyy-MM-dd"): String =
    SimpleDateFormat(format, Locale.ENGLISH).format(this)
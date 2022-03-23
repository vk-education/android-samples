package com.example.lecture5.util

import android.content.Intent
import java.lang.reflect.Field

fun queryLaunchedFlags(intent: Intent): List<String> {
    val declaredFields: Array<Field> = Intent::class.java.declaredFields
    val flags = mutableListOf<String>()
    for (field in declaredFields) {
        if (field.name.startsWith("FLAG_ACTIVITY")) {
            try {
                val flag: Int = field.getInt(null)
                if (intent.flags and flag != 0) {
                    flags += field.name
                }
            } catch (e: IllegalArgumentException) {
                e.printStackTrace()
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            }
        }
    }
    return flags

}
package com.pa_vel.kotlin

import android.content.Context
import androidx.annotation.MainThread

@Deprecated("Will be removed soon")
class Rectangle {
    var width: Int = 0
    var height: Int = 0

    val square: Int
        get() = width * height

    fun print() {
        println("square=$width*$height=$square")
    }
}

fun main() {
    val rectangle = Rectangle()
    rectangle.print()

    rectangle.width = 5
    rectangle.height = 2
    rectangle.print()

    with(LateInit()) {
        onCreate()
        useField()
    }
}

class User(val name: String) {
    var address: String = "unspecified"
        get(): String {
            println("Address was requested")
            return field
        }
        set(value: String) {
            println("Address was changed for $name: $field -> $value")
            field = value
        }
}

class LateInit {
    lateinit var some: String

    fun onCreate() {
        some = System.currentTimeMillis().toString()
    }

    fun useField() {
        println("Some: $some")
    }
}

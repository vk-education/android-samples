package com.pa_vel.kotlin

enum class Color {
    RED, GREEN, BLUE
}

enum class ColorWithCode(val hexCode: Int) {
    RED(0xFF0000),
    GREEN(0x00FF00),
    BLUE(0x0000FF)
}

fun colorPrinter(color: Color) {
    when (color) {
        Color.RED -> println("It's red")
        Color.GREEN -> println("It's green")
        Color.BLUE -> println("It's blue")
        else -> println("Never get here")
    }
}

fun main() {
//    val color = Color()
    colorPrinter(Color.GREEN)

    ColorWithCode.entries.forEach {
        println("Color $it: ${it.hexCode}")
    }
}
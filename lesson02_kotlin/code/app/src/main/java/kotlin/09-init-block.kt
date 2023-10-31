package com.pa_vel.kotlin

class Shape(rawColor: String) {
    val color: String

    init {
        println("Create color $rawColor")
        color = rawColor
//        println("Create color $colorPrint")
    }

    val colorPrint = "Color is $rawColor"

    init {
        println("Create color $colorPrint")
    }
}

fun main() {
    val shape = Shape("Green")
    println(shape.color)
}
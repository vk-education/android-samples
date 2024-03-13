package ru.vk.kotlin_advanced

fun main() {
    val rudeText = "Give me your money"
    println("Give your money".makePlease())
    printClassName(Rectangle())
}

fun String.makePlease() = "$this, please :3"

fun Int.ourIncrement() = this + 1

open class Shape {
    fun getName() = "Original shape"

    fun test() {
        val shape = Shape().getName()
    }
}
class Rectangle: Shape()

fun Shape.getName() = "Shape"
fun Rectangle.getName() = "Rectangle"

fun printClassName(s: Shape) {
    println(s.getName())
}



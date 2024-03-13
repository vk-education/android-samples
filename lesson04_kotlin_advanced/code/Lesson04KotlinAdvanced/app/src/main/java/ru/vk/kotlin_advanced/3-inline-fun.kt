package ru.vk.kotlin_advanced

inline fun testInline(crossinline lambda: () -> Unit) {
    println("run testInline")
    lambda.invoke()
}

fun main() {
    testInline {
        println("Some text")
    }
    println("another text")
}


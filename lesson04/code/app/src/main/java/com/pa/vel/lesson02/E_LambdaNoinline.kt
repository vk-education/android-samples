package com.pa.vel.lesson02

var savedCallback: (() -> Unit)? = null

inline fun <T> processRoutine(
    performance: () -> Unit,
    noinline onFinished: () -> Unit,
) {
    println("Музыка пошла!")
    performance()

    // Без noinline это вызвало бы ошибку: "Illegal usage of inline-parameter"
    savedCallback = onFinished
}

fun main() {
    processRoutine<String>(
        performance = { println("Спортсмен танцует...") },
        onFinished = { println("Оценки отправлены главному судье.") }
    )
    savedCallback?.invoke()
}
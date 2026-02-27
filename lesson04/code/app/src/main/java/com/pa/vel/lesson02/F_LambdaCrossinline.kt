package com.pa.vel.lesson02

// Функция для сложного подсчета баллов в фоновом потоке
inline fun runAsyncScoreCalculation(
    crossinline calculate: () -> Int
) {
    Thread {
        // Без crossinline компилятор выдаст ошибку, так как
        // лямбда может содержать нелокальный return, который "убьет" поток
        val score = calculate()
        println("Финальная оценка: $score")
    }.start()
}

fun judgePerformance() {
    runAsyncScoreCalculation {
        val acrobatics = 15
        // ...
        if (acrobatics < 0) {
            // return // нельзя
            return@runAsyncScoreCalculation 0 // Можно выйти только из самой лямбды - по метке
        }
        acrobatics + 20
    }
    println("Расчет запущен в фоне, ждем результат...")
}

fun main() {
    judgePerformance()
}
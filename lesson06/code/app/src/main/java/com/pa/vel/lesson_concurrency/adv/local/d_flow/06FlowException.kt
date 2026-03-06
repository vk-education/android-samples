package com.pa.vel.lesson_concurrency.adv.local.d_flow

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking

fun main() {

    runBlocking {
        simple()
            .onEach { verifyWithException(it) }
            .catch { e -> println("Caught $e") } // does not catch downstream exceptions
            .collect { value ->
//                verifyWithException(value)
                println(value)
            }

    }

}

private fun verifyWithException(it: Int) {
    check(it <= 1) { "Collected $it" }
}

fun simple(): Flow<Int> = flow {
    for (i in 1..3) {
        println("Emitting $i")
        emit(i)
    }
}
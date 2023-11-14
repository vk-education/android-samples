package com.pa_vel.kotlin06.noui

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlin.coroutines.ContinuationInterceptor


fun main() {
    /*
        Билдеры launch и async – функции-расширения интерфейса CoroutineScope.
     */
    val job: Job = CoroutineScope(
        Job()
            + Dispatchers.Default
            + CoroutineName("I'm the best coroutine")
            + CoroutineExceptionHandler { _, throwable -> println(throwable) }
    ).launch {
        println("[${Thread.currentThread().name}] Launch coroutine: ${print()}")
        delay(1000L)

        launch {
            println("[${Thread.currentThread().name}] Launch child coroutine: ${print()}")
//            throw RuntimeException("ba bah")
            delay(500L)
            println("[${Thread.currentThread().name}] Child finished")
        }

        launch(CoroutineName("I'm child") + Dispatchers.Unconfined) {
            println("[${Thread.currentThread().name}] Launch second child coroutine: ${print()}")
            delay(500L)
            println("[${Thread.currentThread().name}] Second child coroutine finished")
        }
    }

    runBlocking {
        job.join()
    }
}

fun CoroutineScope.print(): String {
//    afun()

    return "Coroutine ${coroutineContext[CoroutineName]}:\n" +
        "\tjob=${coroutineContext[Job]}\n" +
        "\tdispatcher=${coroutineContext[ContinuationInterceptor]}\n" +
        "\thandler=${coroutineContext[CoroutineExceptionHandler]}"
}

suspend fun afun() {
//    try {
//        withContext(Dispatchers.IO) {
//          //
//        }
//    } catch () {
//
//    }

    coroutineScope {

    }
}
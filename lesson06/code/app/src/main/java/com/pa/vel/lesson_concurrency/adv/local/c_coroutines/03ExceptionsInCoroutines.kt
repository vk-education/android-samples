package com.pa.vel.lesson_concurrency.adv.local.c_coroutines

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

suspend fun throwsException(): Int {
    println("Start action")
    delay(100)
    throw RuntimeException("Siuuuuuuuuu")
}

fun main() {
    runBlocking {
        CoroutineScope(Dispatchers.Default + createCoroutineExceptionHandler()).launch {

//            try {
//                launch {
//                    throwsException()
//                }
//            } catch (e: Exception) {
//                println("Caught")
//                null
//            }

            val asyncValue = try {
                async {
                    throwsException()
                }.await()
            } catch (e: Exception) {
                println("Caught")
                null
            }
            println("Async value=${asyncValue}")

        }.join()
    }
    println("End main")
}

fun createCoroutineExceptionHandler(): CoroutineExceptionHandler {
    return CoroutineExceptionHandler { _, throwable -> println("Handler:\n$throwable") }
}





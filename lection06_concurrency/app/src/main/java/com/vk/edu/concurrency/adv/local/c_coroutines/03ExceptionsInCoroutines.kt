package com.vk.edu.concurrency.adv.local.c_coroutines

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

//            val a = try {
                val a = async {
                    throwsException()
                }
//            } catch (e: Exception) {
//                println("Caught")
//                null
//            }


            val b =try {
                a.join()
            } catch (e: Exception) {
                println("Caught 2")
            }
            println("$b")

//    try {
//        job?.await() ?: println("NULL")
//    } catch (e: Exception) {
//        println("Caught 2")
//    }
            println("End coroutine")
        }.join()
    }
    println("End main")
}

fun createCoroutineExceptionHandler(): CoroutineExceptionHandler {
    return CoroutineExceptionHandler { _, throwable -> println("Handler:\n$throwable") }
}





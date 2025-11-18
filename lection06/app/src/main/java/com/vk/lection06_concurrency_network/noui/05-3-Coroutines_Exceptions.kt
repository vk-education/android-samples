@file:OptIn(DelicateCoroutinesApi::class)

package com.vk.lection06_concurrency_network.noui

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {

    try {
        val job = GlobalScope.launch {
            println("Throwing exception from launch")
            try {
                throw IndexOutOfBoundsException() // креш в Android / вывод в stderr на jvm
            } catch (e: Exception) {
                println("CAUGHT - OK")
            }
        }
        job.join()
        println("Job joined")
    } catch (e: Exception) {
        println("Joined failed job")
    }

    val deferred = GlobalScope.async {
        println("Throwing exception from async")
        throw ArithmeticException()
    }
    try {
        deferred.await()
        println("Unreached")
    } catch (e: ArithmeticException) {
        println("Caught ArithmeticException")
    }
}



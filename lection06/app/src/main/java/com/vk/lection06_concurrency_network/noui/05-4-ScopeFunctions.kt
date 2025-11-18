@file:OptIn(DelicateCoroutinesApi::class)

package com.vk.lection06_concurrency_network.noui

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

suspend fun someFun() {
    println("[${Thread.currentThread().name}] Start someFun")
    withContext(Dispatchers.IO) {
        println("[${Thread.currentThread().name}] Start in IO")
        delay(1000)
        throw IllegalStateException("Bah")
        println("Finish in IO")
    }
    println("[${Thread.currentThread().name}] Finish someFun")
}

fun main() = runBlocking {
    val job: Job = CoroutineScope(
        Job()
        + Dispatchers.Unconfined
        + CoroutineName("I'm the best coroutine")
    ).launch {
        try {
            someFun()
        } catch (e: Exception) {
            println("Caught ${e.message}")
        }
    }
    job.join()
}



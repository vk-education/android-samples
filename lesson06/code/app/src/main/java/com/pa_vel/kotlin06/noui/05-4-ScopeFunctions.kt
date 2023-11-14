@file:OptIn(DelicateCoroutinesApi::class)

package com.pa_vel.kotlin06.noui

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.lang.IllegalStateException


fun main() = runBlocking {
    Dispatchers

    val job: Job = CoroutineScope(
        Job()
            + Dispatchers.Default
            + CoroutineName("I'm the best coroutine")
            + CoroutineExceptionHandler { _, throwable -> println(throwable) }
    ).launch {
        println("Coroutine launched [${Thread.currentThread().name}]")
        withContext(Dispatchers.IO) {
            println("withContext started [${Thread.currentThread().name}]")
            try {
                coroutineScope {
                    println("coroutineScope started [${Thread.currentThread().name}]")
                    throw IllegalStateException("Bah")
                }
            } catch (e: Exception) {
                println("caught $e")
            }
            println("withContext finished [${Thread.currentThread().name}]")
        }
        println("launch finished [${Thread.currentThread().name}]")
    }
    println("main after launch [${Thread.currentThread().name}]")
    job.join()
    println("main finished [${Thread.currentThread().name}]")
}



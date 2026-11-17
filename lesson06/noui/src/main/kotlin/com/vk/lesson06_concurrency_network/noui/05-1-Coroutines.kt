package com.vk.lesson06_concurrency_network.noui

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


fun main() = runBlocking {

    val job: Job = launch {
        println("Job launched [${Thread.currentThread().name}]")
        delay(1000L)
        println("Launched job done [${Thread.currentThread().name}]")
    }

    val result: Deferred<String> = async {
        println("Async job launched [${Thread.currentThread().name}]")
        delay(5500L)
        println("Async job done [${Thread.currentThread().name}]")
        "Result"
    }

    println("Start await result [${Thread.currentThread().name}]")

    println("Before await [${Thread.currentThread().name}]")

    result.cancel()

    try {
        println(result.await())
    } catch (e: CancellationException) {
        println(e)
    }

    println("Done [${Thread.currentThread().name}]")
}

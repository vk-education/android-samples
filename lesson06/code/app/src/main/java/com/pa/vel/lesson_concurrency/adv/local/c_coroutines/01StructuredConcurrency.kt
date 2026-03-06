package com.pa.vel.lesson_concurrency.adv.local.c_coroutines

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.random.Random
import kotlin.system.measureTimeMillis

fun main() {
    runBlocking {
        val time = measureTimeMillis {
            computeInParallel()
        }
        launchInParallel()
        println("Completed in $time ms")
    }
}

suspend fun computeInParallel(): Int = coroutineScope {
    val one: Deferred<Int> = async { computeFirstAddendum() }
    val two: Deferred<Int> = async { computeSecondAddendum() }

    one.await() + two.await()
}

suspend fun launchInParallel() = coroutineScope {
    val one: Job = launch { computeFirstAddendum() }
    val two: Job = launch { computeSecondAddendum() }

    one.join()
    two.join()
}

suspend fun computeFirstAddendum(): Int {
    delay(Random.nextLong(500L, 1000L))
    return Random.nextInt()
}

suspend fun computeSecondAddendum(): Int {
    delay(Random.nextLong(500L, 1000L))
    return Random.nextInt()
}

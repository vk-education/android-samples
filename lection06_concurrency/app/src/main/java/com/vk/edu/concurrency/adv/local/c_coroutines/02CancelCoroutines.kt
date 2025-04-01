package com.vk.edu.concurrency.adv.local.c_coroutines

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.runBlocking

fun main() {

    runBlocking(Dispatchers.Default + createHandler()) {
        val asyn: Deferred<Int> = async {
            longAction()
        }
        println("Action launched")
        // ...
        delay(1000L)
        println("Call cancel")
        asyn.cancel()
        println("Cancel called")

        asyn.await()
        println("Action joined")
    }

    println("End main")
}

fun createHandler(): CoroutineExceptionHandler {
    return CoroutineExceptionHandler({ _, throwable -> println(throwable) })
}

suspend fun longAction(): Int {
    return coroutineScope {
//        println("Start action")
        delay(50000L)
        var a = 0
//        while (true) {
        while (isActive) {
            a += 1
        }
        println("Complete action: $a")
        42
//        42
    }
}


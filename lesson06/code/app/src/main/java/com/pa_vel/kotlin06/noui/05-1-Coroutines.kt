package com.pa_vel.kotlin06.noui

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.lang.Exception
import kotlin.time.measureTime


fun main() {
    GlobalScope.launch {
        val time = measureTime {
            val job = try {
                launch {
                    println("Job launched [${Thread.currentThread().name}]")
                    delay(1000L)
                    println("Launched job done [${Thread.currentThread().name}]")
                }
            } catch (e: Exception) {
                println(e)
                null
            }
//    Thread.sleep(1000L)

            val result: Deferred<String> = async {
                println("Async job launched [${Thread.currentThread().name}]")
                delay(1500L)
                println("Async job done [${Thread.currentThread().name}]")
                "Result"
            }
            cancel()
            println("Before join [${Thread.currentThread().name}]")
            println("Before await [${Thread.currentThread().name}]")
            try {
                println(result.await())
            } catch (e: CancellationException) {
                println(e)
            }

            println("Done [${Thread.currentThread().name}]")
        }
        println(time)
    }
}
// all on main?
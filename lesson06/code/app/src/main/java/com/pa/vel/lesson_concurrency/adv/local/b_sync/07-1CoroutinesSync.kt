package com.pa.vel.lesson_concurrency.adv.local.b_sync

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.concurrent.Semaphore
import java.util.concurrent.locks.ReentrantLock
import kotlin.time.measureTime

class CounterDemo {
    var value = 0
        private set

    private val semaphore = Semaphore(1)
    private val mutex = Mutex()

    suspend fun increment() {
        println("${Thread.currentThread().name}: try take lock. is mutex locked=${mutex.isLocked}")
        mutex.withLock {
            println("${Thread.currentThread().name}: mutex lock taken")
            delay(100)
            value++
        }
        println("${Thread.currentThread().name}: released")
    }

    suspend fun incrementJavaSemaphore() {
        val startThread = Thread.currentThread().name
        println("${Thread.currentThread().name}: try take lock. Permits left: ${semaphore.availablePermits()}")
        semaphore.acquire()
        println("${Thread.currentThread().name}: lock acquired")
        delay(100)
        value++
        println("${Thread.currentThread().name}: return lock after delay. Start thread was $startThread")
        semaphore.release()
    }
}

fun main() {
    runBlocking {
        val counter = CounterDemo()
        val toWait = mutableListOf<Job>()

        println("Available cores: ${Runtime.getRuntime().availableProcessors()}")

        val time = measureTime {
            coroutineScope {
                repeat(Runtime.getRuntime().availableProcessors() + 20) {
                    launch(Dispatchers.Default) {
                        counter.increment()
//                        counter.incrementJavaSemaphore()
                    }.also {
                        toWait.add(it)
                    }
                }
                toWait.forEach { it.join() }
            }
        }

        println("Value: ${counter.value}, time=$time")
    }
    println("Finish!")
}

fun computeLong() {
    println("${Thread.currentThread().name}: start long computation")
    val time = measureTime {
        fun fibonacci(n: Int): Long {
            return if (n <= 1) n.toLong() else fibonacci(n - 1) + fibonacci(n - 2)
        }
        fibonacci(40)
    }
    println("${Thread.currentThread().name}: long computation completed")
//    println("Compute $time")
}



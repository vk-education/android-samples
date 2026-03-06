package com.pa.vel.lesson_concurrency.adv.local.b_sync

import java.util.concurrent.CountDownLatch


fun main() {
    val startGate = CountDownLatch(1)
    val nThreads = 10
    val endGate = CountDownLatch(nThreads)

    for (i in 0 until nThreads) {
        Thread {
            println("${Thread.currentThread().name}: $i wait before enter")
            startGate.await()
            try {
                println("${Thread.currentThread().name}: $i inside")
                Thread.sleep(500L)
            } finally {
                endGate.countDown()
            }
        }.start()
    }
    Thread.sleep(3000L)
    println("Main: Allow start")
    startGate.countDown()
    println("Main: Wait completion")
    endGate.await()
    println("Main: Done")
}
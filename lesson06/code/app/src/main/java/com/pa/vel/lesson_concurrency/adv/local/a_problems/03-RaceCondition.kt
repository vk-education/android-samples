package com.pa.vel.lesson_concurrency.adv.local.a_problems

import java.util.concurrent.atomic.AtomicInteger
import kotlin.time.measureTime

// Time=27.035ms***
// Time=79.754792ms***


// Time=68.877917ms***
// Time=61.276375ms***

class CounterDemo() {

//    @Volatile
    val count = AtomicInteger(0)

    fun increment() {
        count.incrementAndGet()
    }
}

fun main() {
    launchCounter(100_000)
}

fun launchCounter(target: Int) {
    val counter = CounterDemo()

    val threads: MutableList<Thread> = mutableListOf()
    repeat(10) {
        threads.add(
            Thread {
                println("[${Thread.currentThread().name}] launched")
                repeat(target) {
                    counter.increment()
                }
            }
        )
    }

    val time = measureTime {
        threads.forEach { it.start() }
        threads.forEach { it.join() }
    }

    println("***Final count is ${counter.count}.\n" +
        "Time=$time***")
}

package com.vk.edu.concurrency.adv.local.a_problems

import java.util.concurrent.atomic.AtomicInteger
import kotlin.time.measureTime

class CounterDemo() {

    var count: AtomicInteger = AtomicInteger(0)
    var count1: Int = 0

    fun increment() {
        count.incrementAndGet()
    }

    @Synchronized
    fun increment1() {
        count1++
    }

//    fun read() = count
//    fun write(value: Int) {
//        count = value
//    }
}

fun main() {
//    repeat(5) {
        launchCounter(100000)
//    }
}

fun launchCounter(target: Int) {
    val counter = CounterDemo()

    val threads: MutableList<Thread> = mutableListOf()
    repeat(30) {
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

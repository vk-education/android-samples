package com.pa.vel.lesson_concurrency.adv.local.a_problems

import kotlin.random.Random

class SomeClass {
    private val logKey = hashCode()

    @Synchronized
    fun someWork() {
        val startTime = System.currentTimeMillis()
        println("[${Thread.currentThread().name}]: Start work with $logKey")
        var i: Long = 2
        repeat(10_000_000) {
            i += Random.nextLong(1000)
        }
        println("[${Thread.currentThread().name}]: " +
            "Work with $logKey finished. Time: ${System.currentTimeMillis() - startTime}")
    }
}

fun main() {
    val startTime = System.currentTimeMillis()

    val threads: MutableList<Thread> = mutableListOf()
    repeat(10) {
        threads.add(
            Thread {
                val someClass = SomeClass()
                someClass.someWork()
            }
        )
    }

    threads.forEach { it.start() }

    threads.forEach { it.join() }

    println("TOTAL TIME: ${System.currentTimeMillis() - startTime}")
}

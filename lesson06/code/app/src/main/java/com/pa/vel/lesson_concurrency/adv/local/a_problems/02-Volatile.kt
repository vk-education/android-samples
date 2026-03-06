package com.pa.vel.lesson_concurrency.adv.local.a_problems

//@Volatile
var done = false

class Worker {
    fun doWork() {
        while (!done) {
            println(1)
        }
    }
}

fun main() {
    Thread {
        Worker().doWork()
        println("Work done")
    }.start()

    val thread = Thread() {
        done = true
    }.also {
        it.start()
    }
    thread.join()
}
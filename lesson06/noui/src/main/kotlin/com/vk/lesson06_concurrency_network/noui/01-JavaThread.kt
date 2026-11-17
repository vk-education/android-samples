package com.vk.lesson06_concurrency_network.noui

fun main() {
    println("hello, i'm ${Thread.currentThread().name} ${Thread.currentThread().isAlive}")

    val thread1 = Thread {
        println("[${Thread.currentThread().name}] Started")
        Thread.sleep(1000L)
        println("[${Thread.currentThread().name}] Do run")
    }

//    thread1.isDaemon = true
    println("[${Thread.currentThread().name}] Start another thread")
    thread1.start()
    println("[${Thread.currentThread().name}] Thread started")
    thread1.join()

    println("[${Thread.currentThread().name}] Thread finished. Is alive=${thread1.isAlive}")
    println("Done")
}


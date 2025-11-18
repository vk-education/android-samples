package com.vk.lection06_concurrency_network.noui

fun main() {
    println("hello, i'm ${Thread.currentThread().name} ${Thread.currentThread().isAlive}")

    val thread1 = Thread {
        Thread.sleep(1000L)
        println("[${Thread.currentThread().name}] Do run")
    }

//    thread1.isDaemon = true
    println("Start another thread")
    thread1.start()
    println("Thread started")
    thread1.join()

    println("Thread finished. Is alive=${thread1.isAlive}")
    println("Done")
}


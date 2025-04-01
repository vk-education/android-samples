package com.vk.edu.concurrency.adv.local.b_sync

import java.util.concurrent.Exchanger
import kotlin.random.Random


fun main() {
    val exchanger = Exchanger<String>()
    // Producer
    Thread {
        println("${Thread.currentThread().name}: launched")
        Thread.sleep(Random.nextLong(2000))
        val received = exchanger.exchange("Hello another thread! I'm ${Thread.currentThread().name}")
        println("${Thread.currentThread().name}: received '$received'")
    }.start()

    // Processor
    Thread {
        println("${Thread.currentThread().name}: launched")
        Thread.sleep(Random.nextLong(2000))
        val received = exchanger.exchange("Hello another thread! I'm ${Thread.currentThread().name}")
        println("${Thread.currentThread().name}: received '$received'")
    }.start()
}


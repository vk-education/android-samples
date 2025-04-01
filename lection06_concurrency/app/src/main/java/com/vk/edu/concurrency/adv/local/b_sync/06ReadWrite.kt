package com.vk.edu.concurrency.adv.local.b_sync

import java.util.concurrent.CountDownLatch
import java.util.concurrent.locks.ReentrantReadWriteLock

fun main() {
    val sharedResource = SharedResource()
    val latch = CountDownLatch(1)

    // Create a few reader threads
    for (i in 1..3) {
        Thread({ sharedResource.readValue() }, "Reader-$i").start()
    }

    // Create a writer thread
    Thread({
        latch.countDown()
        sharedResource.writeValue(10)
    }, "Writer").start()

    // Create another set of reader threads that run after the writer
    for (i in 4..6) {
        latch.await()
        Thread({
            sharedResource.readValue()
        }, "Reader-$i").start()
    }
}

class SharedResource {
    private var value = 0
    private val rwLock = ReentrantReadWriteLock()

    fun readValue() {
        rwLock.readLock().lock()
        try {
            println(Thread.currentThread().name + " reading value: " + value)
            Thread.sleep(1000)
            println(Thread.currentThread().name + " reading value finished")
        } finally {
            rwLock.readLock().unlock()
        }
    }

    fun writeValue(newValue: Int) {
        rwLock.writeLock().lock()
        try {
            value = newValue
            println(Thread.currentThread().name + " writing value: " + value)
            Thread.sleep(1000)
            println(Thread.currentThread().name + " writing value finished")
        } finally {
            rwLock.writeLock().unlock()
        }
    }
}
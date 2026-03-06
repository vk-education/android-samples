package com.pa.vel.lesson_concurrency.adv.local.a_problems

import java.util.LinkedList
import java.util.Queue


fun main() {
    val queue1: Queue<String> = LinkedList<String>()
    val queue2: Queue<String> = LinkedList<String>()

    queue1.add("Item1 from Queue 1")
    queue2.add("Item2 from Queue 2")

    val t1 = Thread {
        moveItem(queue1, queue2)
    }

    val t2 = Thread {
        moveItem(queue2, queue1)
    }

    t1.start()
    t2.start()

    t1.join()
    t2.join()
}

fun moveItem(source: Queue<String>, destination: Queue<String>) {

    println("${Thread.currentThread().name} start")
    synchronized(source) { // thread1 -> queue1; thread2 -> queue2
        println("${Thread.currentThread().name} took first")
        Thread.sleep(100)
        synchronized(destination) {
            println("${Thread.currentThread().name} took second")
            if (source.isNotEmpty()) {
                destination.add(source.poll())
                println("${Thread.currentThread().name} moved an item.")
            }
        }
    }
}

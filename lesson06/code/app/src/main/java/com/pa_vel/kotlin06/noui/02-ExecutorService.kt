package com.pa_vel.kotlin06.noui

import java.util.concurrent.Callable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future


fun main() {
    val task: Callable<String> = Callable<String> {
        println("Start exec in ${Thread.currentThread().name}")
        Thread.sleep(5000L)
        Thread.currentThread().name
    }
    val service: ExecutorService = Executors.newFixedThreadPool(2)
    repeat(5) {
        println("Before")
        val result: Future<String> = service.submit(task)
        println("After")
        println(result.get())
        println("After get")
    }
    service.shutdown()
}
package com.pa_vel.kotlin06.noui

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.reduce
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.random.Random

fun main() = runBlocking {
    val flow = flow<Int> {
        emit(Random.nextInt(10))
        delay(1000L)
        emit(Random.nextInt(10))
        delay(1000L)
        emit(Random.nextInt(10))
        delay(1000L)
        emit(Random.nextInt(10))
        delay(1000L)
        emit(Random.nextInt(10))
    }
    println("Flow created")
    delay(1000L)
    println("Subscribe to flow")

    launch {
        flow.collect {
            println("Value received: $it")
        }
    }

    launch {
        flow.collect {
            println("(2) Value received: $it")
        }
    }

    val job = launch {
        val sum = flow.filter {
            it > 3
        }.map {
            it + 10
        }.catch {
            println(it)
        }.reduce { current, newValue ->
            println("Collect sum received: current=$current, new=$newValue")
            current + newValue
        }.apply {
            println("Sum is $this")
        }
    }
}



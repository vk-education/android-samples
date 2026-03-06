package com.pa.vel.lesson_concurrency.adv.local.d_flow

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.random.Random

data class State(val counter: Int)

class StateProvider {
    private val _state = MutableStateFlow(State(0))
    val state: StateFlow<State> = _state

    fun start() {
        CoroutineScope(Dispatchers.Default).launch {
            while (true) {
                delay(Random.nextLong(900, 1100))
                println("Set value from thread ${Thread.currentThread().name}")
                _state.value = State(_state.value.counter + 1)
            }
        }

//        var i = 0
//        CoroutineScope(Dispatchers.Default).launch {
//            while (true) {
//                i++
//                delay(2)
//            }
//            println(i)
//        }
    }
}

fun main() {
    val provider = StateProvider().also {
        it.start()
    }
    runBlocking {

        // collect example
        val job = launch {
            provider
                .state
                .collect { counterState ->
                    println("${counterState}")
                }
        }

        delay(3000L)
        job.cancel()

        delay(3000L)

        // collectLatest example
        launch {
            provider
                .state
                .collectLatest{ counterState ->
                    println("Start ${counterState}")
                    delay(1000)
                    println("Done ${counterState}")
                }
        }
    }
}
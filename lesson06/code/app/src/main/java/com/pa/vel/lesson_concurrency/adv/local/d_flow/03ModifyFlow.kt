package com.pa.vel.lesson_concurrency.adv.local.d_flow

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.random.Random

@OptIn(ExperimentalCoroutinesApi::class)
fun main() {

//    runBlocking {
//        flowOf(1, 2)
//            .flatMapConcat { flowOf(it, it * 10) }
//            .collect {
//                    println(it)
//            }// [1, 10, 2, 20]
//    }


    runBlocking {
        val flow = StateInDemo()
            .backendState
            .stateIn(this, SharingStarted.Eagerly, -1)

        repeat(3) {
            launch {
                flow
                    .collect {
                        println("Backend state: $it")
                    }
            }
            delay(3000L)
        }
    }
}

class StateInDemo {

    val backendState: Flow<Int> = flow {
        connectToBackend() // some long operation
        while (true) {
            emit(receiveStateUpdateFromBackend().apply {
                println("New state received: $this")
            })
        }
    }


    suspend fun connectToBackend() {
        println("Start connect")
        delay(500L)
        println("Connected")
    }

    suspend fun receiveStateUpdateFromBackend(): Int {
        delay(1000L)
        return Random.nextInt(100)
    }
}


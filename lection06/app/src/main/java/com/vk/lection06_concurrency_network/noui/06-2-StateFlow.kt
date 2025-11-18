package com.vk.lection06_concurrency_network.noui

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.random.Random

private val mutableStateFlow = MutableStateFlow("default")
val stateFlow: StateFlow<String> = mutableStateFlow


@OptIn(DelicateCoroutinesApi::class)
fun main() {
//    val mutableStateFlow = MutableStateFlow("default")
//    val stateFlow: StateFlow<String> = mutableStateFlow

    val flowLaunched = GlobalScope.launch {
        repeat(10) {
            delay(500L)
            mutableStateFlow.value = "Value $it: ${Random.nextInt(20)}"
//            mutableStateFlow.emit("Value $it: ${Random.nextInt(20)}")
        }
    }

    GlobalScope.launch {
        stateFlow.collect {
            println("(1): $it")
        }
    }

    GlobalScope.launch {
        stateFlow.collect {
            println("(2): $it")
        }
    }

    runBlocking {
        delay(10000L)
        flowLaunched.cancel()
    }
}



package com.pa_vel.kotlin06.noui

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.subscribeOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.random.Random

@OptIn(DelicateCoroutinesApi::class)
fun main() {
    val mutableStateFlow = MutableStateFlow("default")

    val stateFlow: StateFlow<String> = mutableStateFlow

    val flowLaunched = GlobalScope.launch {
        repeat(10) {
            delay(500L)
            mutableStateFlow.emit("Value $it: ${Random.nextInt(20)}")
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



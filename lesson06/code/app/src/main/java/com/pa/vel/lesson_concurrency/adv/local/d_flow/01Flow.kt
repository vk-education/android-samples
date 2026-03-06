package com.pa.vel.lesson_concurrency.adv.local.d_flow


import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.random.Random

@OptIn(DelicateCoroutinesApi::class)
fun main() {
    val mutableStateFlow = MutableStateFlow("default")

    val stateFlow: StateFlow<String> = mutableStateFlow

    val flowLaunched = CoroutineScope(Dispatchers.Default).launch {
        repeat(10) {
            delay(500L)

            // SharedFlow emit
            mutableStateFlow.emit("Value $it: ${Random.nextInt(20)}")
            // SharedFlow without suspend
            val wasEmitted = mutableStateFlow.tryEmit("Value $it: ${Random.nextInt(20)}")

            // StateFlow set value
            mutableStateFlow.value = "Value $it: ${Random.nextInt(20)}"
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



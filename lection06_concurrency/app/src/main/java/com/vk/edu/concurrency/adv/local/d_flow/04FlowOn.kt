package com.vk.edu.concurrency.adv.local.d_flow

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking

@OptIn(ExperimentalCoroutinesApi::class, DelicateCoroutinesApi::class)
fun main() {

    runBlocking {
        val main = newSingleThreadContext("CustomMain")
        val customOne = newSingleThreadContext("Custom")
        val customTwo = newSingleThreadContext("Custom2")
        val customThree = newSingleThreadContext("Custom3")

        CoroutineScope(main).launch {
            doAction("launch")
            flowOf("I", "You")
                .onEach { doAction("onEach: $it") }
                .map { it.length }
                .flowOn(customThree)
                .onStart { doAction("onStart") }
                .flowOn(customTwo)
                .flatMapMerge {
                    doAction("flatMapMerge")
                    flowOf(it, it*it, it*it*it)
                        .flowOn(customOne)
                        .onEach { doAction("flow inside flatMapMerge: $it") }
                }
                .flowOn(Dispatchers.IO)
                .collect {
                    doAction("collect $it")
                }
        }.join()

    }
}

fun doAction(key: String) {
    println("${Thread.currentThread().name}: $key")
}


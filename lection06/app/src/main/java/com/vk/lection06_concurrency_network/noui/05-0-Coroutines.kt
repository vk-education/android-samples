package com.vk.lection06_concurrency_network.noui

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    println("[${Thread.currentThread().name}] Start")

    launch {
        launchEmulateUiLooper()
    }

    val api = Api()
    val user = api.fetchUser()
    show(user)
}

suspend fun launchEmulateUiLooper() {
    repeat (20) {
        println("[${Thread.currentThread().name}] 'onDraw' $it")
        delay(1000L)
    }
}

class Api {
    fun fetchUserNoSuspend(): String {
        Thread.sleep(3000L)
        return "John Snow"
    }

    suspend fun fetchUser(): String {
        delay(3000L)
        return "Sirius Black"
    }
}

private fun show(user: String) {
    println("[${Thread.currentThread().name}] User is $user")
}
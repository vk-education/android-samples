package com.example.lecture06.coroutines

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

interface CoroutineDispatchers {

    fun main(): CoroutineDispatcher
    fun default(): CoroutineDispatcher
    fun io(): CoroutineDispatcher
}

class DefaultCoroutineDispatchers : CoroutineDispatchers {
    override fun main(): CoroutineDispatcher = Dispatchers.Main
    override fun default(): CoroutineDispatcher = Dispatchers.Default
    override fun io(): CoroutineDispatcher = Dispatchers.IO
}
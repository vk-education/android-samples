package com.pa_vel.kotlin

sealed class Result

data class Loaded(val data: String) : Result()
data class Error(val description: String) : Result()
object NotModified : Result()

fun parseResult(result: Result) {
    when (result) {
        is Loaded -> println(result.data)
        is Error -> println(result.description)
        is NotModified -> println(result)
    }
}

fun main() {
    val result: Result = Error("Connection lost")
    parseResult(result)
}

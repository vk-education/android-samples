package com.pa_vel.kotlin

import java.io.IOException
import java.lang.Exception

fun main() {
    val map = emptyMap<String, String>()

    try {
        map["crash"]!!.length
    } catch (e: NullPointerException) {
        println("NPE caught")
    } catch (e: Exception) {
        println("Not-NPE caught")
    } finally {
        println("finally called")
    }

    println("After try-catch")
}
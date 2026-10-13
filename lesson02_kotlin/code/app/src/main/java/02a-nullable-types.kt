package com.pa_vel.kotlin

import java.util.Date

//kotlinTypes()
//fun kotlinTypes(): Unit {
//    val a: String? = null
//
//    val unit = println("")
//    println(unit)
//
//    // Any:
//    1.toString()
//    unit.hashCode()
//
////     return Unit
////     return
////     return unit
//}

//givesNothing()
fun givesNothing(): Nothing {
    throw RuntimeException("Always crash")

    val a = com.pa_vel.kotlin.sub.OneMoreResult()
}

fun main() {
//    typeCasting("asd")
    nullableTypes(null)
}

//nullableTypes("a")
fun nullableTypes(input: String?): String? {

    val a: Int? = 1

//    input.chars()

    println(input is Any)
    println(input is Any?)
    println(input is String)
    println(input is String?)

    return input
}

fun typeCasting(input: Any?) {
    println(input is String)
    println(input as String)

    input.chars() // now it's String
}
//typeCasting(2)

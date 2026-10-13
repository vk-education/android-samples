package com.pa_vel.kotlin

import java.util.Date

fun main() {
    val list: List<String> = ArrayList()
    val listInt: List<Int> = ArrayList()

    val generic = GenericClass(Date())
    val generic2 = GenericClass(GenericClass(GenericClass(7)))

    generic.printValues(Date())
    generic2.printValues(generic2.value)

    println(RegularClass().getClass(7))
    println(RegularClass().getClass("7"))
}

class GenericClass<T : Any>(input: T) {
    val value: T = input

    fun printValues(yourT: T): T {
        println("your=$yourT our=$value")
        return yourT
    }
}

class RegularClass {
    fun <T> getClass(input: T): Class<T> {
        return (input as Any).javaClass as Class<T>
    }
}

//fun <T> isA(value: Any) = value is T
//inline fun <reified T> isA(value: Any) = value is T

fun listPrinter(list: List<*>) {
    list.forEach { println(it) }
}

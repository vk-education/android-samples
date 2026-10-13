package com.pa_vel.kotlin

data class SomeClass(val data: String) {

//    override fun equals(other: Any?): Boolean {
//        return (other as? SomeClass)?.data == data
//    }
//
//    override fun hashCode(): Int {
//        return data.hashCode()
//    }
}

fun main() {
    val a = SomeClass("a")
    val b = a
    val a2 = SomeClass("a")

    println(a == a2)
    println(a === a2)
    println(a)
}

package com.pa_vel.kotlin

class SomeClass(val data: String) {
    override fun equals(other: Any?): Boolean {
        return (other as? SomeClass)?.data == data
    }

    override fun hashCode(): Int {
        return data.hashCode()
    }
}

fun main() {
    val a = SomeClass("a")
    val a2 = SomeClass("a")

}

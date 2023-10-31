package com.pa_vel.kotlin

open class Parent {
    public val publicField = "d"
    internal val internalField = "c"
    protected val protectedField = "b"
    private val privateField = "a"
}

class Child : Parent() {
    init {
        println(publicField)
        println(internalField)
        println(protectedField)
//        println(privateField)
    }
}

fun main() {
    with (Parent()) {
        println(publicField)
        println(internalField)
//        println(protectedField)
//        println(privateField)
    }
}


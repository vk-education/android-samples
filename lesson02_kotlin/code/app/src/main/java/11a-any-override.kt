package com.pa_vel.kotlin

class Clazz

fun main() {
    val clazz = Clazz()
    val clazz2 = Clazz()
    println(clazz.toString())
    println(clazz.hashCode())
    println(clazz.equals(clazz))
    println(clazz.equals(clazz2))
}

class Base {
    fun overrideMe() {
        println("Override me, if you can")
    }
}

//class Ext : Base() {
//
//    override fun overrideMe() {
//        println("Overridden by Ext")
//    }
//
//    override fun toString(): String {
//        return "I'm Ext"
//    }
//}
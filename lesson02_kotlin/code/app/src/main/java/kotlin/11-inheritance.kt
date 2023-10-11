package com.pa_vel.kotlin

abstract class Animal(val weight: String) {
    abstract fun whatDoYouSay()
}

class Cat(weight: String) : Animal(weight) {
    override fun whatDoYouSay() {
        println("Meow, meow")
    }
}

class Dog(weight: String) : Animal(weight) {
    override fun whatDoYouSay() {
        println("Woof, woof")
    }
}
class Fox(weight: String) : Animal(weight) {
    override fun whatDoYouSay() {
        println("https://www.youtube.com/watch?v=jofNR_WkoCE")
    }
}

class Fish(weight: String) : Animal(weight) {
    override fun whatDoYouSay() {
        throw NotImplementedError()
    }
}

open class Pet() {
    fun feed() {
        // do feed
    }
}

// !
//class Rabbit : Animal(), Pet() {
//    override fun whatDoYouSay() {
//        println("Fr fr fr")
//    }
//}


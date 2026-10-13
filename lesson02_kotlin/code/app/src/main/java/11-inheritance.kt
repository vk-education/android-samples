package com.pa_vel.kotlin


fun main() {
//    val animal = Animal(2)
    val cat = Cat("10")
    val dog = Dog("20")
    writeWeight(cat)
    writeWeight(dog)
}

fun writeWeight(animal: Animal) {

}


abstract class Animal(val weight: String) {
    abstract fun whatDoYouSay()

    open fun run() {
        // do smth
    }
}

class Cat(weight: String) : Animal(weight) {
    override fun whatDoYouSay() {
        println("Meow, meow")
    }

    override fun run() {

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


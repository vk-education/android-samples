package ru.vk.kotlin_advanced

import kotlin.properties.Delegates

interface Base {
    fun print()
}

class BaseImpl(private val x: Int) : Base {
    override fun print() {
        println("our value is $x")
    }
}

// Класс Delegated может реализовать интерфейс Base,
// делегируя все свои public члены указанному объекту
class Delegated(b: Base) : Base by b

fun main() = runExample(exampleNumber = 5)

fun runExample(exampleNumber: Int) {
    when (exampleNumber) {
        1 -> classDelegateExample()
        2 -> lazyExample()
        3 -> observableExample()
        4 -> vetoableExample()
        5 -> notNullExample()
    }
}

fun classDelegateExample() {
    val b = BaseImpl(10)
    Delegated(b).print()
}


val lazyValue: String by lazy {
    greetMark()
}

private fun greetMark(): String {
    println("Do some hard work once")
    return "Oh, hi, Mark"
}

private fun lazyExample() {
    println("1. First lazy call:")
    println(lazyValue)
    println("2. Second lazy call:")
    println(lazyValue)
}

val fastThreadUnfsaveValue: String by lazy(LazyThreadSafetyMode.NONE) {
    greetMark()
}

var observable by Delegates.observable(1) { prop, oldVal, newVal ->
    println("Observable property changed from $oldVal to $newVal")
}

private fun observableExample() {
    observable = 15
}

var vetoable by Delegates.vetoable(1) { prop, oldVal, newVal ->
    return@vetoable newVal <= 10
}

private fun vetoableExample() {
    println("inital value vetoable = $vetoable")
    vetoable = 15
    println("we tried to change value > 10, vetoable = $vetoable")
    vetoable = 10
    println("we tried to change value < 10, vetoable = $vetoable")
}

var notNull: Int by Delegates.notNull()

private fun notNullExample() {
     //println(notNull) // will fail with IllegalStateException

    notNull = 10
    println(notNull) // 10
}
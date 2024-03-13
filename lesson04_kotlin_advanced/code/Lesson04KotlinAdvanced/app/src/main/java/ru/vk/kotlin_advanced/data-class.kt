package ru.vk.kotlin_advanced

data class Person(val name: String, val age: Int, val city: String = "")


fun main() {
    val person = Person("Jack", 21)
    // show Kotlin bytecode - Decompile

    val set: Set<Person> = setOf(
        Person("Jack", 21),
        Person("Jack", 21),
        Person("Jack", 21),
        Person("Ivan", 21)
    )

    println(set)
}
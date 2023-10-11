package com.pa_vel.kotlin

data class Person(val name: String, val age: Int)


fun main() {
    val person = Person("Jack", 21)
    // show Kotlin bytecode - Decompile

//    person.age
//    person.name
//    val (name, age) = person
//    println("$name, $age")
//
//    person.toString()
//    person.hashCode()
//    val person2 = person.copy()
//    println(person == person2)
}
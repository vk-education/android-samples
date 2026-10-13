package com.pa_vel.kotlin

data class Person(val name: String, val age: Int)




fun main() {
    val person = Person("Jack", 21)

    val pair: Pair<Int, Int> = Pair(1, 2)

    val (first, second) = pair



//    val name = person.name
//    val age = person.age

    val (name, age) = person


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
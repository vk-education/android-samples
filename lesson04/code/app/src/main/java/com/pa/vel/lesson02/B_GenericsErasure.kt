package com.pa.vel.lesson02

fun main() {
    processAthletes()
}

fun processAthletes() {
    val dancers: List<Dancer> = listOf(
        Dancer("Анна", "Спартак")
    )

    val firstDancer: Dancer = dancers[0]
    println(firstDancer.club)
}
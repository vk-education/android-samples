package com.pa.vel.lesson02

open class Athlete(val name: String, val club: String)

class Dancer(name: String, club: String) : Athlete(name, club)

class Footballer(name: String, club: String) : Athlete(name, club)

fun main() {
    val footballDesk = RegistrationDesk<Footballer>()
    val danceDesk = RegistrationDesk<Dancer>()

    footballDesk.register(Footballer("Messi", "Barcelona"))
    footballDesk.register(Footballer("Ronaldo", "Real Madrid"))

    danceDesk.register(Dancer("Мигель", "Триумф"))

    printStartingOrder(footballDesk.participants)
    printStartingOrder(danceDesk.participants)

    val firstFootballer = footballDesk.participants.firstParticipant

    println(firstFootballer?.club)
}

class RegistrationDesk<T> {
    val participants = mutableListOf<T>()

    fun register(participant: T) {
        participants.add(participant)
    }
}

fun <E> printStartingOrder(elements: List<E>) {
    elements.forEachIndexed { index, element ->
        println("${index + 1} - $element")
    }
}

val <T> List<T>.firstParticipant: T?
    get() = this.firstOrNull()
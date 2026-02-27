package com.pa.vel.lesson02

// Только возвращает участников из списка зарегистрированных
interface RegistrationList<out T> {
    fun getNextParticipant(): T

//    fun addParticipant(item: T)
}

// Только принимает участников для оценки
interface JudgingSheet<in T> {
    fun submitScore(participant: T, score: Double)

//    fun getTopParticipant(): T
}

class DancerList : RegistrationList<Dancer> {
    override fun getNextParticipant(): Dancer {
        return Dancer("Анна", "Спартак")
    }
}

class OurList<T> {
    // ...
}

fun main() {

    // Ковариантность
    val dancersOnly: RegistrationList<Dancer> = DancerList()
    // Присваиваем список танцоров переменной, которая ожидает список ЛЮБЫХ спортсменов (Athlete)
    val athletesList: RegistrationList<Athlete> = dancersOnly
    val next: Athlete = athletesList.getNextParticipant()
    println(next.name)

//    athletesList.addParticipant(Footballer("Олег", "Динамо")) // t не позволяет сделать так
//    val currentDancer = internalList[0] -> иначе ClassCastException (Footballer cannot be cast to Dancer)


    // Контрвариантность
    val generalSheet = object : JudgingSheet<Athlete> {
        var topAthlete: Athlete = Footballer("Олег", "Динамо")

        override fun submitScore(p: Athlete, score: Double) { /* ... */ }
//        override fun getTopParticipant(): Athlete = topAthlete
    }

    val dancerSheet: JudgingSheet<Dancer> = generalSheet
//    val bestDancer: Dancer = dancerSheet.getTopParticipant() // а top - это Footballer
}
package com.pa.vel.lesson02

fun main() {

    val dancers = listOf(
        Dancer("Иван", "Спартак"),
        Footballer("Олег", "Динамо")
    )

    val spartakCount = countAthletesByClub(dancers, "Спартак") // Работает!
    // countAthletesByClub(listOf(1, 2, 3), "Спартак") // ❌ Ошибка компиляции
}


class AType<T> where T : Athlete & Comparable {

}

fun <T : Athlete> countAthletesByClub(athletes: List<T>, targetClub: String): Int {
    return athletes.count { it.club == targetClub }
}
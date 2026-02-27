package com.pa.vel.lesson02


// ==========================================
// 1. Infix функции
// ==========================================

// Инфиксная функция расширения для создания пары
infix fun Dancer.partnersWith(partner: Dancer): Pair<Dancer, Dancer> {
    return Pair(this, partner)
}

// ==========================================
// 2. Type Aliases (Псевдонимы типов)
// ==========================================

typealias CategoryName = String
typealias TotalScore = Int
// Сложный тип превращается в понятный бизнес-термин
typealias JudgingProtocol = Map<CategoryName, List<Pair<Athlete, TotalScore>>>

// Функция, использующая псевдоним
fun processResults(results: JudgingProtocol) {
    println("Система: Обработка протокола для категорий: ${results.keys.joinToString()}")
    // Пример чтения данных
    results.forEach { (category, participants) ->
        println("  -> В категории '$category' выступило ${participants.size} пар(ы)")
    }
}

// ==========================================
// 3. Operator Overloading (Перегрузка операторов)
// ==========================================

data class RoutineScore(val acrobatics: Int, val dance: Int) {
    // Перегружаем оператор +
    operator fun plus(other: RoutineScore): RoutineScore {
        return RoutineScore(
            acrobatics = this.acrobatics + other.acrobatics,
            dance = this.dance + other.dance
        )
    }
}

// ==========================================
// 4. Value Classes (Inline classes)
// ==========================================

@JvmInline
value class JudgeId(val id: String)

@JvmInline
value class AthleteId(val id: String)

@JvmInline
value class Score(val points: Int) {
    init {
        require(points >= 0) { "Оценка не может быть отрицательной" }
    }
}

// Функция со строгой типизацией аргументов
fun submitScore(judge: JudgeId, athlete: AthleteId, score: Score) {
    println("Система судейства: Судья [${judge.id}] поставил [${score.points}] баллов участнику [${athlete.id}]")
}

// Функция со строгой типизацией аргументов
fun submitScore(judge: String, athlete: String, score: String) {
    println("Система судейства: Судья [${judge.id}] поставил [${score.points}] баллов участнику [${athlete.id}]")
}


// ==========================================
// Основная функция для демонстрации на лекции
// ==========================================

fun main() {
    println("--- Слайд 1: Infix функции ---")
    val ivan = Dancer("Иван", "Спартак")
    val anna = Dancer("Анна", "Спартак")

    // Вызов инфиксной функции читается как текст
    val couple = ivan partnersWith anna
    println("Сформирована пара: ${couple.first.name} и ${couple.second.name}")


    println("\n--- Слайд 2: Type Aliases ---")
    // Имитация данных протокола соревнований
    val mockProtocol: JudgingProtocol = mapOf(
        "В-класс микст" to listOf(Pair(ivan, 45)),
        "А-класс микст" to listOf()
    )
    processResults(mockProtocol)


    println("\n--- Слайд 3: Operator Overloading ---")
    val technicalJudgeScore = RoutineScore(acrobatics = 25, dance = 10)
    val artisticJudgeScore = RoutineScore(acrobatics = 5, dance = 30)

    // Магия перегрузки оператора "+"
    val finalScore = technicalJudgeScore + artisticJudgeScore
    println("Оценка техника: $technicalJudgeScore")
    println("Оценка артистизм: $artisticJudgeScore")
    println("Итоговая сумма: Акробатика=${finalScore.acrobatics}, Танец=${finalScore.dance}")


    println("\n--- Слайд 4: Value Classes ---")
    val judge = JudgeId("J-42")
    val athlete = AthleteId("A-109")
    val score = Score(45)

    // Передать параметры в неправильном порядке физически невозможно
    submitScore(judge, athlete, score)

    // ❌ Ошибка компиляции, если раскомментировать:
    // submitScore(athlete, judge, score)
}
package com.pa.vel.lesson02

// 1. Интерфейс поведения (то, что мы будем делегировать)
interface WarmupRoutine {
    fun prepareForStart()
}

// 2. Конкретные реализации (Делегаты)
// Реализация для обычной танцевальной разминки
class StretchingRoutine : WarmupRoutine {
    override fun prepareForStart() {
        println("-> Выполняется базовая растяжка мышц и подготовка связок...")
    }
}

// Реализация для сложной акробатической разминки
class AcrobaticsWarmup : WarmupRoutine {
    override fun prepareForStart() {
        println("-> Выполняется разминка для акробатики: сальто, фляки, страховка...")
    }
}

// ==========================================
// Основной класс с использованием Class Delegation
// ==========================================

// 3. Делегирование интерфейса объекту (by)
// HipHopDancer наследует Athlete и одновременно реализует WarmupRoutine, 
// полностью перекладывая работу на переданный объект warmupBehavior.
class HipHopDancer(
    name: String,
    club: String,
    warmupBehavior: WarmupRoutine,
) : Athlete(name, club), WarmupRoutine by warmupBehavior



fun main() {
    // Создаем разные варианты поведения (разминки)
    val basicWarmup = StretchingRoutine()
    val hardWarmup = AcrobaticsWarmup()

    // Собираем спортсменов как конструктор Lego, 
    // передавая им нужное поведение в момент создания.
    val anna = HipHopDancer("Анна", "Спартак", basicWarmup)
    val ivan = HipHopDancer("Иван", "Лидер", hardWarmup)

    // Вызываем методы. 
    // В классе HipHopDancer мы не написали ни одной строчки кода для prepareForStart(),
    // но компилятор сам проксирует вызовы в нужные объекты-делегаты.

    println("К старту готовится: ${anna.name} (${anna.club})")
    anna.prepareForStart() // Вызовет логику StretchingRoutine

    println("\nК старту готовится: ${ivan.name} (${ivan.club})")
    ivan.prepareForStart() // Вызовет логику AcrobaticsWarmup
}
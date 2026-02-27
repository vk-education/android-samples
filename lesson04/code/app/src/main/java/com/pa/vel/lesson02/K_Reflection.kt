package com.pa.vel.lesson02

import kotlin.reflect.full.memberFunctions
import kotlin.reflect.full.memberProperties

// Какой-то класс, о котором мы хотим узнать информацию в рантайме
class JudgeToReflect {
    private val role = "Главный судья" // [cite: 902]

    fun announceScore(): String { // [cite: 903]
        return "Оценка: 10 баллов!" // [cite: 904]
    }
}

fun main() {
    val obj = JudgeToReflect() // [cite: 910]

    // 1. Получаем KClass (метаинформацию о классе)
    val kClass = obj.javaClass.kotlin //

    // Можно вывести вообще все члены класса, которые есть внутри
    println("Доступные члены: " + kClass.members.joinToString { it.name }) // [cite: 917]

    // 2. Ищем свойство и метод по обычному тексту (строке)
    val prop = kClass.memberProperties.find { it.name == "role" } // [cite: 918]
    val method = kClass.memberFunctions.find { it.name == "announceScore" } // [cite: 919]

    // 3. Динамически получаем значение и вызываем функцию
    // Используем безопасный вызов ?., так как find мог вернуть null
    println("Reflected: role=${prop?.get(obj)} announceScore=${method?.call(obj)}") // [cite: 920]
}
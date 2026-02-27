package com.pa.vel.lesson02


fun main() {
    var pair: Pair<String, Number> = Pair("hello", 7.7)
    pair = "hello" to 7.7

    var a = 7 - 7
    var b = "7" + "7"

    // Возвращают объект: apply и also

    // apply: Настройка UI-компонента (контекст this)
    val promoSheet = HalfScreenPromoSheet().apply {
        title = "Специальное предложение"
        isCancelable = false
        showExpanded = true
    }
    promoSheet.show()

    println()

    // also: Сайд-эффекты и логирование (контекст it)
    val newSportDancer = SportDancer("Анна", "Спартак").also {
        println("Система: зарегистрирован новый участник ${it.name}")
        Database.save(it)
    }

    // Возвращают результат лямбды: let и run

    // let: Выполнение кода, только если объект не null
    val currentWinner: SportDancer? = Tournament.getTopParticipant()

    currentWinner?.let {
        awardMedal(it)
        updateRankings(it.club)
    }

    println()

    // run: Серия вызовов методов с возвратом результата
    val isReadyForPerformance = newSportDancer.run {
        warmUp()
        stretch()
        checkHeartRate() < 120 // Вернет Boolean
    }
    println("Спортсмен готов к выходу: $isReadyForPerformance")

    println("\n--- Слайд 4: with ---")

    val currentSportDancer = SportDancer("Иван", "Спартак")



    // with: Группируем обращения к свойствам объекта
    val announcementString = with(currentSportDancer) {
        "На паркет приглашается $name, представляющий клуб '$club'!"
    }

    println(announcementString)
}


class SportDancer(name: String, club: String) : Athlete(name, club) {
    fun warmUp() {
        println("$name: Разминка начата")
    }

    fun stretch() {
        println("$name: Растяжка выполнена")
    }

    fun checkHeartRate(): Int {
        return 115
    }
}

class HalfScreenPromoSheet {
    var title: String = ""
    var isCancelable: Boolean = true
    var showExpanded: Boolean = false

    fun show() {
        println("UI: Показываем шторку '$title' (expanded=$showExpanded, cancelable=$isCancelable)")
    }
}

object Database {
    fun save(SportDancer: SportDancer) {
        println("БД: Участник ${SportDancer.name} успешно сохранен")
    }
}

object Tournament {
    fun getTopParticipant(): SportDancer? {
        return SportDancer("Виктория", "Лидер")
    }
}

fun awardMedal(SportDancer: SportDancer) {
    println("Церемония: Медаль вручается участнику ${SportDancer.name}!")
}

fun updateRankings(clubName: String): String {
    println("Рейтинг: Клуб '$clubName' получает дополнительные очки")
    return "42"
}
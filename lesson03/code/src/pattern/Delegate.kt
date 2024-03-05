package pattern

fun main() {
    println("Введите тип действия:")
    val actionType = "set wake up timer"
    val timer: Timer = Timer()

    if (actionType.equals("set wake up timer", ignoreCase = true)) {
        timer.action = WakeUpAction()
    } else if (actionType.equals("set chicken timer", ignoreCase = true)) {
        timer.action = ChickenIsReadyAction()
    }

    timer.start()
}

class Timer {
    var action: TimerAction? = null

    fun start() {
        if (isTime()) {
            action!!.onTime()
        }
    }

    private fun isTime(): Boolean {
        return true
    }
}

interface TimerAction {
    fun onTime()
}

class WakeUpAction : TimerAction {
    override fun onTime() {
        println("Пора вставать!")
    }
}

class ChickenIsReadyAction : TimerAction {
    override fun onTime() {
        println("Цыплёнок готов!")
    }
}
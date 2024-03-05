package pattern.structural

import java.io.InputStreamReader

fun main() {
    LoggingNotifier(
        FancyNotifier(
            ConsoleNotifier()
        )
    ).notify("Hello, World!")
}

interface Notifier {
    fun notify(message: String)
}

class ConsoleNotifier : Notifier {
    override fun notify(message: String) {
        println(message)
    }
}

class LoggingNotifier(private val notifier: Notifier) : Notifier {
    override fun notify(message: String) {
        notifier.notify(message)
        println("LOG - $message") // Like a logger
    }
}

class FancyNotifier(private val notifier: Notifier) : Notifier {
    override fun notify(message: String) {
        val border = "-".repeat(message.length)
        notifier.notify("""
            $border
            $message
            $border
        """.trimIndent())
    }
}
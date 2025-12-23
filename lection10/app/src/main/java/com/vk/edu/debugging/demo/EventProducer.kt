package com.vk.edu.debugging.demo

object EventProducer {
    private val listeners: MutableList<EventListener> = mutableListOf()

    fun registerListener(listener: EventListener) {
        listeners.add(listener)
    }

    fun clear() {
        listeners.clear()
    }

    fun notifyListeners() {
        listeners.forEach {
            it.onEvent()
        }
    }
}

fun interface EventListener {
    fun onEvent()
}

package ru.hse.lection04.businesslayer


/**
 * Класс, которые содержит логику для работу с подписчиками
 * @param <LISTENER> любой подписчик
 */
open class AbstractCallbackProvider<LISTENER> {
    companion object {
        protected const val EMPTY = 0
    }


    protected val listeners = mutableSetOf<LISTENER>()


    /***
     * Зарегестрировать подписчика
     * @param listener подписчик
     */
    fun register(listener: LISTENER) {
        val lastSize = listeners.size
        listeners.add(listener)
        if (lastSize == EMPTY) {
            setActivation(true)
        }
    }

    /**
     * Удалить подписчика
     * @param listener подписчик
     */
    fun unregister(listener: LISTENER) {
        listeners.remove(listener)
        if (listeners.size == EMPTY) {
            setActivation(false)
        }
    }

    /**
     * В зависимости от изменения набора подписчиков вызовется этот метод
     * @param value true - означает что появились слушатели и можно начать процесс отслеживания даных и уведомления. false - когда список подписчиков пуст
     */
    protected open fun setActivation(value: Boolean) {}
}
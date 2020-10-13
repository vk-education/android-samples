package ru.hse.lection03.businesslayer

import ru.hse.lection03.objects.Droid
import java.util.*

class DroidRepository private constructor() {
    companion object {
        // Константы
        const val DATA_SIZE = 100
        const val STATE_CRITICAL = 0.8f

        // простенький singleton
        val instance by lazy { DroidRepository() }

        // Для генерации случайных чисел
        protected val RANOMIZER = Random()
    }


    // ленивая инициализации для списка Дроидов
    protected val droidList by lazy { initializeData() }


    // получить список Дроидов
    fun list() = droidList

    // получить дроида по индексу
    fun item(index: Int) = droidList[index]


    // Функция инициализации списка дроидов
    protected fun initializeData(): List<Droid> {
        val data = mutableListOf<Droid>()

        // Наполняем лист в цикле
        for (position in 0 until DATA_SIZE) {

            // Генерим имя дроида
            val name = "Droid $position"

            // Получаем случайное число, и определяем состояние дроида
            val state = when (RANOMIZER.nextFloat() >= STATE_CRITICAL) {
                true -> Droid.STATE_REMOVED
                false -> Droid.STATE_NEW
            }

            // Создаем дроида и добавляем его в список
            val droid = Droid(name, state)
            data.add(droid)
        }

        return data
    }
}
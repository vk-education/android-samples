package com.pa.vel.lesson02
import kotlin.properties.ReadWriteProperty
import kotlin.random.Random
import kotlin.reflect.KProperty

class ClassWithDb {
    // Классический подход (Много бойлерплейта)
    private var _clubName: String? = null
    var clubName: String
        get() {
            if (_clubName == null) _clubName = loadClubFromDb()
            return _clubName!!
        }
        set(value) {
            _clubName = value
            saveClubToDb(value)
        }

    private fun saveClubToDb(value: String) {
        // ... save code
    }

    private fun loadClubFromDb(): String? {
        return Random.nextInt().toString()
    }
}

class DatabaseDelegate(private val key: String) : ReadWriteProperty<Any?, String> {

    // Внутреннее поле для кэширования значения, чтобы не дергать БД при каждом чтении
    private var cachedValue: String? = null

    // Перехватываем обращение (get)
    override fun getValue(thisRef: Any?, property: KProperty<*>): String {
        if (cachedValue == null) {
            cachedValue = loadFromDb(key)
        }
        return cachedValue!!
    }

    // Перехватываем присваивание (set)
    override fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        cachedValue = value
        saveToDb(key, value)
    }

    // --- Заглушки для имитации работы с реальной БД ---

    private fun loadFromDb(key: String): String {
        println("БД: Загружаем данные по ключу [$key]...")
        return "Спартак" // Возвращаем дефолтное значение для примера
    }

    private fun saveToDb(key: String, value: String) {
        println("БД: Сохраняем значение [$value] по ключу [$key]")
    }
}
package ru.hse.lection04.datalayer

import android.content.Context
import android.content.SharedPreferences

/**
 * Класс для работы информацией и настройках
 */
class ConnectivityDataAccessor(context: Context) {
    companion object {
        protected const val PREFERENCES_NAME = "PREFERENCES_CONNECTIVITY"
        protected const val KEY_TRACK_ENABLED = "TRACK_ENABLED"
        protected const val VALUE_TRACK_ENABLED_DEFAULT = false
    }


    // Хранение параметров будем делать в SharedPreferences
    protected var mPreferences: SharedPreferences

    // Кэшируем значение, что бы меньше ходить в SharedPreferences
    protected var mTrackEnabled: Boolean = false


    init {
        mPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
        mTrackEnabled = initializeTrackingValue()
    }


    /**
     * Какое текущее состояние трекинга
     * @return true/false
     */
    fun isTrackEnabled(): Boolean {
        return mTrackEnabled
    }

    /**
     * Запомнить новое состояние трекинга
     * @param value состояние трекинга
     */
    fun setTrackEnabled(value: Boolean) {
        mPreferences.edit()
                .putBoolean(KEY_TRACK_ENABLED, value)
                .apply()
        mTrackEnabled = value
    }

    /**
     * Инициализация данных
     */
    protected fun initializeTrackingValue(): Boolean {
        return mPreferences.getBoolean(KEY_TRACK_ENABLED, VALUE_TRACK_ENABLED_DEFAULT)
    }
}
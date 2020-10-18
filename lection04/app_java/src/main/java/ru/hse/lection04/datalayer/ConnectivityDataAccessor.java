package ru.hse.lection04.datalayer;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Класс для работы информацией и настройках
 */
public class ConnectivityDataAccessor {
    protected static final String PREFERENCES_NAME = "PREFERENCES_CONNECTIVITY";

    protected static final String KEY_TRACK_ENABLED = "TRACK_ENABLED";
    protected static final boolean VALUE_TRACK_ENABLED_DEFAULT = false;


    // Хранение параметров будем делать в SharedPreferences
    protected SharedPreferences mPreferences;

    // Кэшируем значение, что бы меньше ходить в SharedPreferences
    protected boolean isTrackEnabled;


    public ConnectivityDataAccessor(Context context) {
        mPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);

        isTrackEnabled = initializeTrackingValue();
    }


    /**
     * Какое текущее состояние трекинга
     * @return true/false
     */
    public boolean isTrackEnabled() {
        return isTrackEnabled;
    }

    /**
     * Запомнить новое состояние трекинга
     * @param value состояние трекинга
     */
    public void setTrackEnabled(boolean value) {
        mPreferences.edit()
                .putBoolean(KEY_TRACK_ENABLED, value)
                .apply();

        isTrackEnabled = value;
    }

    /**
     * Инициализация данных
     */
    protected boolean initializeTrackingValue() {
        return mPreferences.getBoolean(KEY_TRACK_ENABLED, VALUE_TRACK_ENABLED_DEFAULT);
    }
}

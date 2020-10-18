package ru.hse.lection04.businesslayer.connectivity

import android.content.Context
import android.net.ConnectivityManager
import ru.hse.lection04.businesslayer.AbstractCallbackProvider
import ru.hse.lection04.datalayer.ConnectivityDataAccessor
import ru.hse.lection04.objects.ConnectivityInfo

/**
 * Базовая спеуицикация для работы с информацией о соединении. С небольшой имплементацией - в виде информации о состоянии трекинга
 */
open class AbstractConnectivityProvider(context: Context) : AbstractCallbackProvider<AbstractConnectivityProvider.IListener?>() {
    /**
     * Слушаьель, который будет уведомляться при изменении данных о соединении
     */
    interface IListener {
        fun networkUpdated(provider: AbstractConnectivityProvider?, info: ConnectivityInfo?)
    }

    protected val mConnectivityManager: ConnectivityManager
    protected val mAccessor: ConnectivityDataAccessor


    init {
        mConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        mAccessor = ConnectivityDataAccessor(context)
    }


    /**
     * Состояние трекинга
     * @return true/false
     */
    val isTrackEnabled: Boolean
        get() = mAccessor.isTrackEnabled()

    /**
     * Проверить и изменить запись о том, какое состояние трекере
     * @param value новое значение
     * @return true - если записалось новое значение. false, если обновление не произошло
     */
    fun setTrackEnabled(value: Boolean): Boolean {
        if (isTrackEnabled == value) {
            return false
        }

        mAccessor.setTrackEnabled(value)
        return true
    }
}
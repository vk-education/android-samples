package ru.hse.lection04.businesslayer.connectivity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkInfo
import ru.hse.lection04.objects.ConnectivityInfo

/**
 * Имплементация, для поддержания работы логики до 24 версии API
 */
class ConnectivityPre24ApiProvider(protected val mContext: Context) : AbstractConnectivityProvider(mContext) {
    protected val mReceiver = ConnectivityReceiver()


    override fun setActivation(value: Boolean) {
        super.setActivation(value)
        if (value) {
            mReceiver.register(mContext)
        } else {
            mReceiver.unregister(mContext)
        }
    }

    /**
     * Обработать изменение состоянии сети
     * @param fallbackInfo информация, которую получили используя старую логику
     */
    protected fun networkChanged(fallbackInfo: NetworkInfo?) {
        val networkInfo = mConnectivityManager.activeNetworkInfo
        val info = extractInfo(networkInfo, fallbackInfo)
        for (listener in listeners) {
            listener?.networkUpdated(this, info)
        }
    }

    /**
     * Превращаем полученные данные в наш объект
     * @param networkInfo основная информация о состоянии
     * @param fallbackInfo информация, которую получили используя старую логику
     * @return наш объект с ифнормацией о соединении
     */
    protected fun extractInfo(networkInfo: NetworkInfo?, fallbackInfo: NetworkInfo?): ConnectivityInfo {
        val info = ConnectivityInfo()
        if (fallbackInfo != null && fallbackInfo.isConnectedOrConnecting) {
            info.type = networkInfo!!.typeName
        } else if (networkInfo == null && fallbackInfo == null) {
            info.type = ConnectivityInfo.Companion.NO_CONNECTION
        } else if (networkInfo != null && fallbackInfo != null && networkInfo.isConnectedOrConnecting != fallbackInfo.isConnectedOrConnecting) {
            info.type = fallbackInfo.typeName
        }
        return info
    }

    /**
     * Подписчик на изменение состояния покдлючения, при помощи
     */
    protected inner class ConnectivityReceiver : BroadcastReceiver() {
        // Система уведомляет об изменения в методе onReceive
        override fun onReceive(context: Context, intent: Intent) {
            val fallbackInfo = intent.getParcelableExtra<NetworkInfo>(ConnectivityManager.EXTRA_NETWORK_INFO)
            networkChanged(fallbackInfo)
        }

        /**
         * Зарегестрировать BroadcastReceiver
         * @param context Context для регистрации
         */
        fun register(context: Context) {
            val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
            context.registerReceiver(this, filter)
        }

        /**
         * Перестать получать события
         * @param context Context для остановки
         */
        fun unregister(context: Context) {
            context.unregisterReceiver(this)
        }
    }
}
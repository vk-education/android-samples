package ru.hse.lection04.businesslayer.connectivity

import android.content.Context
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi
import ru.hse.lection04.objects.ConnectivityInfo

/**
 * Имплементация, для поддержания работы логики от 24 версии API
 */
@RequiresApi(api = Build.VERSION_CODES.N)
class ConnectivityProvider(context: Context) : AbstractConnectivityProvider(context) {
    companion object {
        protected const val TYPE_UNKNOWN = "UNKNOWN"
        private val TRANSPORT_NAMES = arrayOf(
                "CELLULAR",
                "WIFI",
                "BLUETOOTH",
                "ETHERNET",
                "VPN",
                "WIFI_AWARE",
                "LOWPAN",
                "TEST"
        )
    }


    protected val mConnectivityCallback = ConnectivityCallback()


    override fun setActivation(value: Boolean) {
        super.setActivation(value)
        if (value) {
            mConnectivityManager.registerDefaultNetworkCallback(mConnectivityCallback)
        } else {
            mConnectivityManager.unregisterNetworkCallback(mConnectivityCallback)
        }
    }

    /**
     * Обработать изменение состоянии сети
     * @param network Полученные данные
     */
    protected fun networkChanged(network: Network?) {
        val info = extractInfo(network)
        for (listener in listeners) {
            listener?.networkUpdated(this, info)
        }
    }

    /**
     * Превращаем полученные данные в наш объект
     * @param network Полученные данные
     * @return наш объект с ифнормацией о соединении
     */
    protected fun extractInfo(network: Network?): ConnectivityInfo {
        val info = ConnectivityInfo()
        if (network != null) {
            val capabilities = mConnectivityManager.getNetworkCapabilities(mConnectivityManager.activeNetwork)
            if (capabilities != null) {
                info.type = extractType(capabilities)
            }
        }
        return info
    }

    protected fun extractType(capabilities: NetworkCapabilities): String {
        if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
            return TRANSPORT_NAMES[NetworkCapabilities.TRANSPORT_CELLULAR]
        } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
            return TRANSPORT_NAMES[NetworkCapabilities.TRANSPORT_WIFI]
        } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
            return TRANSPORT_NAMES[NetworkCapabilities.TRANSPORT_ETHERNET]
        } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN)) {
            return TRANSPORT_NAMES[NetworkCapabilities.TRANSPORT_VPN]
        } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH)) {
            return TRANSPORT_NAMES[NetworkCapabilities.TRANSPORT_BLUETOOTH]
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI_AWARE)) {
                return TRANSPORT_NAMES[NetworkCapabilities.TRANSPORT_WIFI_AWARE]
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_LOWPAN)) {
                return TRANSPORT_NAMES[NetworkCapabilities.TRANSPORT_LOWPAN]
            }
        }
        return TYPE_UNKNOWN
    }

    /**
     * Подписчик на изменение состояния покдлючения, при помощи
     */
    protected inner class ConnectivityCallback : NetworkCallback() {
        override fun onCapabilitiesChanged(network: Network, networkCapabilities: NetworkCapabilities) {
            super.onCapabilitiesChanged(network, networkCapabilities)
            networkChanged(network)
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            networkChanged(null)
        }
    }
}
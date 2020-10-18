package ru.hse.lection04.businesslayer

import android.content.Context
import android.os.Build
import ru.hse.lection04.businesslayer.connectivity.AbstractConnectivityProvider
import ru.hse.lection04.businesslayer.connectivity.ConnectivityPre24ApiProvider
import ru.hse.lection04.businesslayer.connectivity.ConnectivityProvider

/**
 * Поставщик провадеров
 */
object ServiceLocator {
    lateinit var connectivityProvider: AbstractConnectivityProvider
        internal set

    lateinit var channelProvider: ChannelProvider
        internal set

    lateinit var logProvider: LogProvider
        internal set

    /**
     * Инициализировать все провайдеры
     * @param context Context, некоторым провадерам для инициализации нужны дополнительные объекты.
     */
    fun initialize(context: Context) {
        connectivityProvider = initializeConnectivityProvider(context)
        channelProvider = ChannelProvider()
        logProvider = LogProvider(context)
    }

    /**
     * Инициализировать нужный ConnectivityProvider, в зависимости от версии Android
     * @param context Context для инициализации и для ображения
     * @return Имплементация, которая подходит под версию Android
     */
    internal fun initializeConnectivityProvider(context: Context): AbstractConnectivityProvider {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            ConnectivityProvider(context)
        } else {
            ConnectivityPre24ApiProvider(context)
        }
    }
}
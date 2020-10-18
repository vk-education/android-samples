package ru.hse.lection04

import android.app.Application
import android.content.Context
import ru.hse.lection04.businesslayer.ServiceLocator

class ConnectivityApplication : Application() {
    companion object {
        const val TAG = "ConnectivityApplication"
    }


    // Если требуется обернуть контекст, то можно это сделать здесь
    override fun attachBaseContext(base: Context) {
//        final Context wrapped = new MyContextWrapper(base);
        super.attachBaseContext(base)
    }

    override fun onCreate() {
        super.onCreate()

        // Инициализация нашего класса ServiceLocator - он будет поставлять провайдеры
        ServiceLocator.initialize(this)

        // Поскольку у нас есть уведомления в статусбаре, новые Android требуют для них "Каналы"
        ServiceLocator.channelProvider.initializeChannels(this)
    }
}
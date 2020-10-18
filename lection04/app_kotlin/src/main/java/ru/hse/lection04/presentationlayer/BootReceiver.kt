package ru.hse.lection04.presentationlayer

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import ru.hse.lection04.businesslayer.ServiceLocator

/**
 * Получаем уведомление о том, что система загружена.
 * Если надо - запускаме сервис для трекинга.
 */
class BootReceiver : BroadcastReceiver() {
    protected val mConnectivityProvider = ServiceLocator.connectivityProvider


    override fun onReceive(context: Context, intent: Intent) {
        val isRealInvoke = Intent.ACTION_BOOT_COMPLETED == intent.action
        if (!isRealInvoke) {
            // Защита от того, что бы ресивер не использовался другим фильтром.
            return
        }

        // Если у нас установлен трэкинг, надо будет запустить сервис
        if (mConnectivityProvider.isTrackEnabled) {
            val serviceIntent: Intent = ConnectivityService.Companion.newInstance(context)
            ContextCompat.startForegroundService(context, serviceIntent)
        }
    }
}
package ru.hse.lection04.businesslayer

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi

/**
 * Провайдер для работы с каналами
 */
class ChannelProvider {
    /**
     * Инициализация каналов для уведомлений начиная с Android O.
     * @param context Context для обращения к NotificationManager
     */
    fun initializeChannels(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            checkAndCreateChannels(context)
        }
    }

    /**
     * Код для создания каналов, работает начиная с Android O.
     * @param context Context для обращения к NotificationManager
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    protected fun checkAndCreateChannels(context: Context) {
        // Получаем NotificationManager ищ контектса
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Перебираем все наши каналы из enum-а
        for (info in Channels.values()) {
            // Пробуем достать канал из NotificationManager
            var channel = manager.getNotificationChannel(info.id)

            // Если его там нет, то создадим и добавим свой
            if (channel == null) {
                // Создаем новый канал
                val name = context.getString(info.nameResId)
                channel = NotificationChannel(
                        info.id
                        , name
                        , NotificationManager.IMPORTANCE_DEFAULT
                )
                channel.lightColor = Color.BLUE
                channel.lockscreenVisibility = Notification.VISIBILITY_PRIVATE


                // Добавляем канал в NotificationManager
                manager.createNotificationChannel(channel)
            }
        }
    }
}
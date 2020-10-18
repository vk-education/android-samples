package ru.hse.lection04.presentationlayer

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import ru.hse.lection04.R
import ru.hse.lection04.businesslayer.Channels
import ru.hse.lection04.businesslayer.ServiceLocator
import ru.hse.lection04.businesslayer.connectivity.AbstractConnectivityProvider
import ru.hse.lection04.objects.ConnectivityInfo
import java.util.*

/**
 * Сервис для остлеживания изменения состояния соединения в фоне
 */
class ConnectivityService : Service() {
    companion object {
        /**
         * Идентификатор уведомления, которое будет оторажаться в статусбаре
         */
        protected const val FOREGROUND_ID = 1338
        protected const val MESSAGE_CREATE = "SERVICE: CREATED"
        protected const val MESSAGE_DESTROY = "SERVICE: DESTROYED"
        protected const val MESSAGE_NO_CONNECTIVITY = "CONNECTIVITY: NO CONNECTIVITY"
        protected const val MESSAGE_CONNECTIVITY_PATTERN = "CONNECTIVITY: type = %s "
        fun newInstance(context: Context?): Intent {
            return Intent(context, ConnectivityService::class.java)
        }
    }


    protected val mConnectivityProvider = ServiceLocator.connectivityProvider
    protected val mLogProvider = ServiceLocator.logProvider

    protected val mConnectivityListener = ConnectivityListener()


    override fun onCreate() {
        super.onCreate()

        // Foreground сервис требует отображение уведомления в статусбаре
        val notification = buildNotification()
        startForeground(FOREGROUND_ID, notification)

        // Делаем запись в Лог о том, что сервис создан. Значит трекинг начался
        writeLog(MESSAGE_CREATE)

        // Подписываемся на изменение состояния подключения
        mConnectivityProvider.register(mConnectivityListener)
    }

    override fun onDestroy() {
        super.onDestroy()

        // Отписываемся от получения состояния подключения
        mConnectivityProvider.unregister(mConnectivityListener)

        // Делаем запись в Лог о том, что сервис уничтожен. Значит трекинг прекратился
        writeLog(MESSAGE_DESTROY)
    }

    override fun onBind(intent: Intent): IBinder? {
        // Один из возможных интерфейсов общения. Поскольку у нас сервис выполняет фоновую работу, байндер ему не нужен.
        return null
    }

    /**
     * Конструктор уведомдения
     * @return Notification с информацией о сервисе
     */
    protected fun buildNotification(): Notification {
        // Куда ведем, если кликнули на уведомление
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0)

        // Канал, в рамках которого запускаем уведомление
        val channel = Channels.CONNECTIVITY

        // Констурируем уведомление при помощи класса, который обеспечивает обратную совместимость
        return NotificationCompat.Builder(this, channel.id)
                .setContentTitle(getText(R.string.caption_service_notification_title))
                .setContentText(getText(R.string.caption_service_notification_description))
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentIntent(pendingIntent)
                .setTicker(getText(R.string.caption_service_notification_ticker))
                .build()
    }

    /**
     * Обрабатываем обновление подключения
     * @param info Информация о подключении
     */
    protected fun connectivityUpdated(info: ConnectivityInfo?) {
        val message = if (info == null) {
            MESSAGE_NO_CONNECTIVITY
        } else {
            String.format(MESSAGE_CONNECTIVITY_PATTERN, info.type)
        }

        writeLog(message)
    }

    /**
     * Пишем в LogProvшder
     * @param message новая запись
     */
    protected fun writeLog(message: String?) {
        val time = Calendar.getInstance().time
        mLogProvider.write(time, message)
    }

    protected inner class ConnectivityListener : AbstractConnectivityProvider.IListener {
        override fun networkUpdated(provider: AbstractConnectivityProvider?, info: ConnectivityInfo?) {
            connectivityUpdated(info)
        }
    }
}
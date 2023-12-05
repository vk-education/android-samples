package company.vk.lesson08

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationChannelGroupCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.BigPictureStyle
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat

object Notifications {
    enum class Groups(val title: String) {
        SIMPLE("SIMPLE group"),
        PLAYER("PLAYER group")
    }

    @SuppressLint("InlinedApi")
    enum class Channels(val title: String, val description: String, val importance: Int, val group: Groups) {
        SIMPLE("SIMPLE title", "SIMPLE description", NotificationManager.IMPORTANCE_DEFAULT, Groups.SIMPLE),
        PLAYER("PLAYER title", "PLAYER description", NotificationManager.IMPORTANCE_HIGH, Groups.PLAYER)
    }

    fun initialize(context: Context) {
        val manager = NotificationManagerCompat.from(context)
        manager.initializeChannelGroups()
        manager.initializeChannels()
    }

    fun requestPermission(activity: Activity): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            // Текущая версия Android не требует разрешение для показа уведомлений
            return false
        }

        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            // Разрешение уже выдано
            return false
        }

        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.POST_NOTIFICATIONS)) {
            // Означает что пользователь уже видел предложение дать разрешение, но отказался.
            // Гугл рекомендует для уведомлений после отказа от уведомлений, больше не спрашивать про это
            return false
        }

        return true
    }

    fun showSimpleNotification(context: Context) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // Разрешения нет
            return
        }


        val image = BitmapFactory.decodeResource(context.resources, R.drawable.sample_image)
        val style = BigPictureStyle().bigPicture(image).bigLargeIcon(image)


        val notificationId = Channels.SIMPLE.hashCode()
        val builder = NotificationCompat.Builder(context, Channels.SIMPLE.name).apply {
            priority = NotificationCompat.PRIORITY_DEFAULT

            setSmallIcon(R.mipmap.ic_notification)
            setContentTitle("Title")
            setContentText("Content")
            setGroup(Groups.SIMPLE.name)
            setOngoing(true)
            setStyle(style)

//            setContentIntent(pendingIntent)

            // cancel action
            val cancelIntent = ActionBroadcastReceiver.newIntent(context, notificationId, ActionBroadcastReceiver.COMMAND_CANCEL)
            val cancelPendingIntent = PendingIntent.getBroadcast(context, 0, cancelIntent, PendingIntent.FLAG_MUTABLE)
            addAction(0, "Cancel", cancelPendingIntent)
        }

        val manager = NotificationManagerCompat.from(context)
        manager.notify(notificationId, builder.build())
    }

    fun dismissNotification(context: Context, notificationId: Int) {
        val manager = NotificationManagerCompat.from(context)
        manager.cancel(notificationId)
    }

    private fun NotificationManagerCompat.initializeChannels() {
        val actualChannelIds = Channels.values().map { it.name }
        deleteUnlistedNotificationChannels(actualChannelIds)

        Channels.values().forEach {
            if (getNotificationChannel(it.name) != null) {
                return@forEach
            }

            val builder = NotificationChannelCompat.Builder(it.name, it.importance).apply {
                setName(it.title)
                setDescription(it.description)
                setGroup(it.group.name)
            }

            createNotificationChannel(builder.build())
        }
    }

    private fun NotificationManagerCompat.initializeChannelGroups() {
        val actualGroupIds = Groups.values().map { it.name }.toSet()
        notificationChannelGroupsCompat
            .filterNot { actualGroupIds.contains(it.id) }
            .forEach { deleteNotificationChannelGroup(it.id) }

        Groups.values().forEach {
            if (getNotificationChannelGroup(it.name) != null) {
                return@forEach
            }

            val builder = NotificationChannelGroupCompat.Builder(it.name).apply {
                setName(it.title)
            }

            createNotificationChannelGroup(builder.build())
        }
    }
}
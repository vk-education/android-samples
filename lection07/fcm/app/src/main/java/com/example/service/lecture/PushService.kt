package com.example.service.lecture

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.service.lecture.MainActivity.Companion.CHANNEL_ID
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class PushService : FirebaseMessagingService() {


    override fun onNewToken(token: String) {
        Log.d("FcmToken", "Token = $token")
    }


    override fun onMessageReceived(message: RemoteMessage) {
        Log.d("Message", "$message")
        val notification = message.notification
        val title = notification?.title.orEmpty()
        val body = notification?.body.orEmpty()
        showCallNotification(title, body)
    }

    @SuppressLint("MissingPermission")
    private fun showCallNotification(title: String, body: String) {
        val notificationManagerCompat = NotificationManagerCompat.from(this)
        notificationManagerCompat.notify(NOTIF_ID, createNotification(title, body))
    }

    private fun createNotification(title: String, body: String) =
        NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(body)
            .setSmallIcon(android.R.drawable.ic_menu_call)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

    private companion object {
        const val NOTIF_ID = 1011
    }
}

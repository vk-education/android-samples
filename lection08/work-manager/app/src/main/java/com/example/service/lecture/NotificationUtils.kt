package com.example.service.lecture

import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

fun createNotification(context: Context, workerName: String, msg: String): Notification {
    createGroupAndChannel(context)

    return NotificationCompat.Builder(context, CHANNEL_ID)
        .setContentTitle(workerName)
        .setContentText(msg)
        .setSmallIcon(android.R.drawable.stat_notify_sync)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .build()
}

private fun createGroupAndChannel(context: Context) {
    val notificationManager = NotificationManagerCompat.from(context)

    val myChannel = NotificationChannelCompat.Builder(
        /* id = */ CHANNEL_ID,
        /* importance = */ NotificationManager.IMPORTANCE_HIGH
    )
        .setName("Call")
        .setDescription("This single channel for this group")
        .build()

    notificationManager.createNotificationChannel(myChannel)
}

private const val CHANNEL_ID = "__my_work_channel_id__"

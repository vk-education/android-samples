package com.mycompany.servicesexample

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat

class MyService : Service() {

    private val tag = "MyService"
    private var started = false
    private val channel = "MyChannel2"
    private val id = 111




    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(tag, "onStartCommand intent = $intent, flags = $flags, startId = $startId")

        if (!started) {
            Thread(Runnable {
                var i = 0
                while (true) {
                    Log.d(tag, "New count = $i")
                    Thread.sleep(1000)
                    i++
                }
            }).start()
            started = true
        }

        Log.d(tag, "CREATE NOTIF")

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel(channel, "My channel", NotificationManager.IMPORTANCE_MIN)
            val notificationService: NotificationManager =
                getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationService.createNotificationChannel(channel)
        }

//        val style = NotificationCompat.BigTextStyle()
//            .setBigContentTitle("BIG CONTENT TITLE")
//            .setSummaryText("SUMMARY")

        val notification = NotificationCompat.Builder(this, channel)
            .setContentTitle("I'm doing job")
            .setContentText("sdkljhaskjdfhjkasdhfjadskhfkjasdhfkahsdkjfhadskjhfkasdjhfadskjfhkalsdjhfadskjhfkajdshfjkasdhfkjalsdhfkjladshflkajsdfhjkasdhfjkasdhfjkashdfksdahfkjl")
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
//            .setStyle(style)
            .build()

        startForeground(id, notification)

        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(tag, "onDestroy")
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(tag, "onCreate thread ${Thread.currentThread().name}")
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}
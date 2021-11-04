package superp.techpark.ru.lesson8

import android.app.IntentService
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat

/**
 * IntentService, который с задержкой в 5 секунд выводит в Logcat переданную строку в верхнем или
 * нижнем регистре. Также сервис поддерживает работу в foreground режиме с отображением
 * уведомления в статус-баре.
 */
class DemoIntentService : IntentService(TAG) {
    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }

    override fun onHandleIntent(intent: Intent?) {
        if (intent == null || TextUtils.isEmpty(intent.action)) {
            return
        }
        val data = intent.extras!!.getString(EXTRA_STRING)
        val isForeground = intent.getBooleanExtra(EXTRA_FOREGROUND, false)
        if (isForeground) {
            startForeground(1, notification)
        }
        Log.d(TAG, String.format("Start action '%s'", intent.action))
        when (intent.action) {
            ACTION_TO_UPPER -> handleToUpper(data)
            ACTION_TO_LOWER -> handleToLower(data)
        }
        if (isForeground) {
            stopForeground(true)
        }
    }

    private fun handleToUpper(data: String?) {
        try {
            Thread.sleep(5000L)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(applicationContext, "Result ${data!!.uppercase()}", Toast.LENGTH_SHORT).show()
        }
        Log.d(TAG, "Result: " + data!!.toUpperCase())
    }

    private fun handleToLower(data: String?) {
        try {
            Thread.sleep(5000L)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(applicationContext, "Result ${data!!.lowercase()}", Toast.LENGTH_SHORT)
                .show()
        }
        Log.d(TAG, "Result: " + data!!.toLowerCase())
    }

    private val notification: Notification
        private get() = NotificationCompat.Builder(this, createNotificationChannel("01", "def"))
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(getString(R.string.working))
                .build()

    private fun createNotificationChannel(channelId: String, name: String): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val chan = NotificationChannel(channelId,
                    name, NotificationManager.IMPORTANCE_NONE)
            chan.lightColor = Color.BLUE
            chan.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
            val service = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            service.createNotificationChannel(chan)
            channelId
        } else {
            ""
        }
    }

    companion object {
        private const val TAG = "DemoIntentService"
        const val ACTION_TO_UPPER = "TO_UPPER"
        const val ACTION_TO_LOWER = "TO_LOWER"
        const val EXTRA_STRING = "STRING"
        const val EXTRA_FOREGROUND = "FOREGROUND"
    }
}
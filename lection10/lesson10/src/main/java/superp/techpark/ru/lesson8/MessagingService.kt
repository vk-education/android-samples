package superp.techpark.ru.lesson8

import android.app.NotificationManager
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d(TAG, "From: $remoteMessage")
        if (remoteMessage.data.isNotEmpty()) {
            Log.d(TAG, "Message data payload: " + remoteMessage.data)
            //
        }

        // Check if message contains a notification payload.
        if (remoteMessage.notification != null) {
            val title = remoteMessage.notification!!.title
            val body = remoteMessage.notification!!.body
            Log.d(TAG, "Message Notification Body: $body")
            showMessageNotification(title, body)
        }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(TAG, "Refreshed token: $token")

        // отправляем токен на наш сервер
        sendRegistrationToServer(token)
    }

    private fun sendRegistrationToServer(refreshedToken: String) {
        Log.d(TAG, "Should send token to out server $refreshedToken")
    }

    fun showMessageNotification(title: String?, message: String?) {
        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val builder = NotificationCompat.Builder(this, MainActivity.CHANNEL_DEFAULT)
        builder.setSmallIcon(R.drawable.ic_announcement)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
        manager.notify(NOTIFICATION_ID_SIMPLE, builder.build())
    }

    companion object {
        const val TAG = "Firebase"
        private const val NOTIFICATION_ID_SIMPLE = 4
    }
}
package superp.techpark.ru.lesson8

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : AppCompatActivity() {
    var mMessageCount = 0
    private var mMessageEdit: EditText? = null
    private var mManager: NotificationManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mMessageEdit = findViewById(R.id.edit_message)
        mManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        initChannels()
        findViewById<View>(R.id.button_simple).setOnClickListener { showSimpleNotification() }
        findViewById<View>(R.id.button_clear).setOnClickListener { clearAll() }
        findViewById<View>(R.id.button_image).setOnClickListener { showBigImageNotification() }
        findViewById<View>(R.id.button_send).setOnClickListener { showMessageNotification() }
        findViewById<View>(R.id.button_ongoing).setOnClickListener { showOngoingNotification() }
        findViewById<View>(R.id.button_service).setOnClickListener { intentServiceDemo() }
        val action = intent.action
        if (!TextUtils.isEmpty(action)) mManager!!.cancel(NOTIFICATION_ID_ONGOING)
        FirebaseMessaging.getInstance().token
                .addOnCompleteListener(OnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        Log.d(MessagingService.TAG, "Fetching FCM registration token failed", task.exception)
                        return@OnCompleteListener
                    }
                    val newToken = task.result
                    // send to backend
                    Log.d(MessagingService.TAG, "FCM Token: $newToken")
                })
    }

    private fun showSimpleNotification() {
        val builder = NotificationCompat.Builder(this, CHANNEL_LIKES)
        builder.setSmallIcon(R.drawable.ic_like)
                .setContentTitle(getString(R.string.user_name))
                .setContentText(getString(R.string.new_like))
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setAutoCancel(true)
        addDefaultIntent(builder)
        mManager!!.notify(NOTIFICATION_ID_SIMPLE, builder.build())
    }

    fun clearAll() {
//        mManager!!.cancelAll()
        mManager!!.cancel(/* message id */ mMessageCount) /* удалит последнее уведомление */
    }

    fun showMessageNotification() {
        mMessageCount++
        val messageToShow = mMessageEdit!!.text.toString()
        val largeIcon = BitmapFactory.decodeResource(resources, R.drawable.example_large_icon)
        val builder = NotificationCompat.Builder(this, CHANNEL_MESSAGES)
                .setLargeIcon(largeIcon)
                .setSmallIcon(R.drawable.ic_message_black)
                .setContentTitle(getString(R.string.message_name))
                .setContentText(messageToShow)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setVibrate(VIBRATION_PATTERN)
                .setLights(LIGHT_COLOR_ARGB, 1000, 1000)
                .setColor(resources.getColor(R.color.colorAccent))
                .setAutoCancel(true)
        val style = NotificationCompat.BigTextStyle()
        style.bigText(messageToShow)
        style.setSummaryText(getString(R.string.message_summary, mMessageCount))
        builder.setStyle(style)
        addDefaultIntent(builder)
        addMessageIntent(builder, messageToShow)
        mManager!!.notify(mMessageCount, builder.build())
    }

    private fun showBigImageNotification() {
        val builder = NotificationCompat.Builder(this, CHANNEL_DEFAULT)
        builder.setSmallIcon(R.drawable.ic_announcement)
                .setContentTitle(getString(R.string.big_image))
                .setContentText(getString(R.string.simple_description))
                .setColor(resources.getColor(R.color.colorAccent))
                .setAutoCancel(true)
        val largeIcon = BitmapFactory.decodeResource(resources, R.drawable.example_large_icon)
        val image = BitmapFactory.decodeResource(resources, R.drawable.bg_5)
        val style = NotificationCompat.BigPictureStyle()
        style.bigPicture(image)
        style.bigLargeIcon(largeIcon)
        builder.setStyle(style)
        addDefaultIntent(builder)
        mManager!!.notify(NOTIFICATION_ID_IMAGE, builder.build())
    }

    fun intentServiceDemo() = startActivity(Intent(this, IntentServiceActivity::class.java))

    fun showOngoingNotification() {
        val builder = NotificationCompat.Builder(this, CHANNEL_DEFAULT)
        builder.setSmallIcon(R.drawable.ic_announcement)
                .setContentTitle(getString(R.string.ongoing))
                .setContentText(getString(R.string.infinite_progress))
                .setColor(resources.getColor(android.R.color.darker_gray))
                .setOngoing(true)
                .setProgress(100, 54, false)
        addDefaultIntent(builder)
        addRemoveProgressIntent(builder)
        mManager!!.notify(NOTIFICATION_ID_ONGOING, builder.build())

        Thread { // sample usage :(
            Thread.sleep(2000)
            builder.setProgress(100, 100, false)
            mManager!!.notify(NOTIFICATION_ID_ONGOING, builder.build())
        }.start()
    }

    private fun addMessageIntent(builder: NotificationCompat.Builder, message: String) {
        val contentIntent = Intent(this, MessageActivity::class.java)
        contentIntent.putExtra(MessageActivity.EXTRA_TEXT, message)
        val flags = PendingIntent.FLAG_CANCEL_CURRENT
        val pendingIntent = PendingIntent.getActivity(
                this,
                1,
                contentIntent,
                flags)
        builder.addAction(NotificationCompat.Action(R.drawable.ic_like, getString(R.string.show), pendingIntent))
    }

    private fun addRemoveProgressIntent(builder: NotificationCompat.Builder) {
        val contentIntent = Intent(this, MainActivity::class.java)
        contentIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        contentIntent.action = ACTION_REMOVE_PROGRESS
        val flags = PendingIntent.FLAG_CANCEL_CURRENT
        val pendingIntent = PendingIntent.getActivity(this, 2, contentIntent, flags)
        builder.addAction(NotificationCompat.Action(0, getString(R.string.remove), pendingIntent))
    }

    private fun addDefaultIntent(builder: NotificationCompat.Builder) {
        val contentIntent = Intent(this, MainActivity::class.java)
        contentIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        contentIntent.putExtra("TIME", System.currentTimeMillis())
        val flags = PendingIntent.FLAG_UPDATE_CURRENT // отменить старый и создать новый
        val pendingIntent = PendingIntent.getActivity(this, 0, contentIntent, flags)
        builder.setContentIntent(pendingIntent)
    }

    fun initChannels() {
        if (Build.VERSION.SDK_INT < 26) return
        val defaultChannel = NotificationChannel(
                CHANNEL_DEFAULT,
                getString(R.string.channel_default_name), NotificationManager.IMPORTANCE_DEFAULT)
        mManager!!.createNotificationChannel(defaultChannel)
        val likesChannel = NotificationChannel(CHANNEL_LIKES,
                getString(R.string.channel_likes_name), NotificationManager.IMPORTANCE_LOW)
        likesChannel.description = getString(R.string.channel_likes_description)
        mManager!!.createNotificationChannel(likesChannel)
        val messagesChannel = NotificationChannel(CHANNEL_MESSAGES,
                getString(R.string.channel_messages_name), NotificationManager.IMPORTANCE_HIGH)
        messagesChannel.description = getString(R.string.channel_messages_description)
        messagesChannel.vibrationPattern = VIBRATION_PATTERN
        messagesChannel.enableLights(true)
        messagesChannel.enableVibration(true)
        messagesChannel.lightColor = LIGHT_COLOR_ARGB
        mManager!!.createNotificationChannel(messagesChannel)
    }

    companion object {
        const val CHANNEL_DEFAULT = "default"
        const val CHANNEL_MESSAGES = "messages"
        const val CHANNEL_LIKES = "likes"
        private const val ACTION_REMOVE_PROGRESS = "remove_progress"
        private val VIBRATION_PATTERN = longArrayOf(0, 100, 50, 100)
        private const val LIGHT_COLOR_ARGB = Color.GREEN
        private const val NOTIFICATION_ID_SIMPLE = 0
        private const val NOTIFICATION_ID_MESSAGE = 1
        private const val NOTIFICATION_ID_IMAGE = 2
        private const val NOTIFICATION_ID_ONGOING = 3
    }
}
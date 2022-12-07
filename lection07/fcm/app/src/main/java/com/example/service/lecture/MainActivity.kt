package com.example.service.lecture

import android.Manifest.permission.POST_NOTIFICATIONS
import android.app.NotificationManager
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.os.PersistableBundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationChannelGroupCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) return@OnCompleteListener
            Log.d("FcmToken", "Token = ${task.result}")
        })
        createGroupAndChannel()
    }

    private fun createGroupAndChannel() {
        val notificationManager = NotificationManagerCompat.from(this)

        val myChannel = NotificationChannelCompat.Builder(
            /* id = */ CHANNEL_ID,
            /* importance = */ NotificationManager.IMPORTANCE_HIGH
        )
            .setName("Call")
            .setDescription("This single channel for this group")
            .build()

        notificationManager.createNotificationChannel(myChannel)
    }
    companion object {
        const val CHANNEL_ID = "__my_call_channel_id__"
    }
}
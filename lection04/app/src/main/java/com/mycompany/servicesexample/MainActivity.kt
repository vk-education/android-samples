package com.mycompany.servicesexample

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : AppCompatActivity() {
    private val tag = MainActivity::class.java.name

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val work = OneTimeWorkRequestBuilder<MyWorker>()
//            .setBackoffCriteria()
//            .build()
//
//        WorkManager.getInstance(this)

        val intent = Intent(this, MyService::class.java)
//        startService(intent)
//        startService(intent)
//        startService(intent)
//        startService(intent)
//        startService(intent)
//        startService(intent)
//        startService(intent)
//        startService(intent)

        ContextCompat.startForegroundService(this, intent)


//        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
//            if (!task.isSuccessful) {
//                Log.e(tag, "Fetching FCM registration token failed", task.exception)
//                return@OnCompleteListener
//            }
//
//            val token = task.result
//            Log.d(tag, "get token = $token")
//        })

    }
}
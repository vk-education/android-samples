package com.example.service.lecture

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.os.PersistableBundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private var service: GeoService? = null

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, binder: IBinder?) {
            service = (binder as? GeoService.LocalBinder)?.service
            startCollect()
        }

        override fun onServiceDisconnected(name: ComponentName?) {
        }

    }

    private fun startCollect() {
        val textLocation = findViewById<TextView>(R.id.location_text_view)

        lifecycleScope.launch {
            service?.lastLocation?.flowWithLifecycle(
                lifecycle,
                Lifecycle.State.STARTED
            )?.collect {
                textLocation.text = it.lastLocation.toString()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<CheckBox>(R.id.make_foreground_checkbox).setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                service?.makeForeground()
            } else {
                service?.stopForeground()
            }
        }

        findViewById<Button>(R.id.start_button).setOnClickListener {
            val startIntent = GeoService.startIntent(this)
            startService(startIntent)
        }
        findViewById<Button>(R.id.stop_button).setOnClickListener {
            val startIntent = GeoService.stopIntent(this)
            startService(startIntent)
        }
        findViewById<Button>(R.id.bind_button).setOnClickListener {
            val startIntent = GeoService.startIntent(this)
            bindService(startIntent, serviceConnection, BIND_AUTO_CREATE)
        }
        findViewById<Button>(R.id.unbind_button).setOnClickListener {
            service = null
            unbindService(serviceConnection)
        }

    }
}
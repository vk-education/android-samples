package com.example.service.lecture

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
import android.content.pm.ServiceInfo
import android.graphics.BitmapFactory
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.os.Looper
import android.util.Log
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationChannelGroupCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.BigTextStyle
import androidx.core.app.NotificationManagerCompat
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class GeoService : Service() {

    private val binder = LocalBinder()
    private var isForeground = false

    private val _lastLocation = MutableSharedFlow<LocationResult>(replay = 1)
    private val callback = object : LocationCallback() {
        @SuppressLint("MissingPermission")
        override fun onLocationResult(result: LocationResult) {
            Log.d(TAG, "onLocationResult() called with: result = $result")
            runCatching {
                _lastLocation.tryEmit(result)
            }
            if (isForeground) {
                NotificationManagerCompat.from(this@GeoService).notify(
                    NOTIF_ID,
                    createNotification(result.lastLocation.toString())
                )
            }
        }
    }
    val lastLocation = _lastLocation.asSharedFlow()

    override fun onUnbind(intent: Intent?): Boolean {
        return super.onUnbind(intent)
    }

    override fun onRebind(intent: Intent?) {
        super.onRebind(intent)
    }

    override fun onBind(intent: Intent?): IBinder = binder

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.getSerializableExtra(ACTION_KEY) as? Action) {
            Action.Start -> {
                startLocationCollector()
            }
            Action.Stop -> {
                stopLocationCollector()
            }
            null -> { /* do nothing */
            }
        }

        return START_REDELIVER_INTENT
    }

    private fun stopLocationCollector() {
        val fusedLocationProvider = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationProvider.removeLocationUpdates(callback)
        stopForeground()
        stopSelf()
    }

    @SuppressLint("MissingPermission")
    private fun startLocationCollector() {
        val fusedLocationProvider = LocationServices.getFusedLocationProviderClient(this)
        val request = LocationRequest.Builder(/* intervalMillis = */ 1000)
            .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
            .build()

        fusedLocationProvider.requestLocationUpdates(request, callback, Looper.getMainLooper())
    }

    fun makeForeground() {
        createGroupAndChannel()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            startForeground(
                NOTIF_ID,
                createNotification(""),
                ServiceInfo.FOREGROUND_SERVICE_TYPE_LOCATION
            )
        } else {
            startForeground(NOTIF_ID, createNotification(""))
        }
        isForeground = true
    }

    private fun createNotification(location: String): Notification {
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Current location")
            .setContentText(location)
            .setStyle(BigTextStyle().bigText(location))
            .setContentIntent(
                PendingIntent.getActivity(
                    this,
                    1,
                    Intent(this, MainActivity::class.java).apply {
                        flags = FLAG_ACTIVITY_REORDER_TO_FRONT
                    },
                    PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT

                )
            )
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setOngoing(true)
            .setSmallIcon(android.R.drawable.ic_menu_mylocation)
            .addAction(
                NotificationCompat.Action(
                    null, "Stop", PendingIntent.getService(
                        this,
                        1,
                        stopIntent(this),
                        PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT

                    )
                )
            )
            .setLargeIcon(
                BitmapFactory.decodeResource(
                    resources,
                    android.R.drawable.ic_menu_mylocation
                )
            )
            .build()

    }

    private fun createGroupAndChannel() {
        val notificationManager = NotificationManagerCompat.from(this)
        val myGroup = NotificationChannelGroupCompat.Builder(GROUP_ID)
            .setName("Geo group")
            .setDescription("This group of geo service channels")
            .build()

        val myChannel = NotificationChannelCompat.Builder(
            /* id = */ CHANNEL_ID,
            /* importance = */ NotificationManager.IMPORTANCE_HIGH
        )
            .setName("Current location")
            .setDescription("This single channel for this group")
            .setGroup(GROUP_ID)
            .build()

        notificationManager.createNotificationChannelGroup(myGroup)
        notificationManager.createNotificationChannel(myChannel)
    }

    fun stopForeground() {
        isForeground = false
        stopForeground(STOP_FOREGROUND_REMOVE)
    }

    inner class LocalBinder : Binder() {
        val service: GeoService
            get() = this@GeoService
    }

    private enum class Action {
        Start,
        Stop
    }

    companion object {
        private const val ACTION_KEY = "action"
        private const val TAG = "GeoService"
        private const val NOTIF_ID = 101

        private const val CHANNEL_ID = "__my_channel_id__"
        private const val GROUP_ID = "__my_group_id__"

        fun startIntent(context: Context): Intent =
            createIntent(context).putExtra(ACTION_KEY, Action.Start)

        fun stopIntent(context: Context): Intent =
            createIntent(context).putExtra(ACTION_KEY, Action.Stop)

        private fun createIntent(context: Context) =
            Intent(context, GeoService::class.java)
    }
}
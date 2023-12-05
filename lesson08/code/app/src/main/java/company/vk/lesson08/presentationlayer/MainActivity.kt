package company.vk.lesson08.presentationlayer

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.Process
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import company.vk.lesson08.ActionBroadcastReceiver
import company.vk.lesson08.Alarms
import company.vk.lesson08.BackgroundService
import company.vk.lesson08.BoundService
import company.vk.lesson08.ForegroundService
import company.vk.lesson08.JobSchedulerService
import company.vk.lesson08.Notifications
import company.vk.lesson08.R
import company.vk.lesson08.TimerWork

class MainActivity : AppCompatActivity() {
    protected val notificationPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission(), ::proceedNotificationPermission)

    protected val boundServiceConnection by lazy { BoundService.Connection(this) }

    protected val actionMapper = mapOf(
        R.id.simple_notification to { Notifications.showSimpleNotification(this) },

        R.id.background_service to { BackgroundService.sendCommand(this, BackgroundService.COMMAND_SHOW_PID, "Activity.pid=${Process.myPid()}") },

        R.id.foreground_service_start to { ForegroundService.executeCommand(this, ForegroundService.COMMAND_START) },
        R.id.foreground_service_stop to { ForegroundService.executeCommand(this, ForegroundService.COMMAND_STOP) },
        R.id.foreground_service_destroy to { ForegroundService.executeCommand(this, ForegroundService.COMMAND_DESTROY) },

        R.id.bound_service_toast to { boundServiceConnection.controller()?.showToast("Activity.pid=${Process.myPid()}") },

        R.id.job_scheduler_start to { JobSchedulerService.scheduleSimpleJob(this, System.currentTimeMillis()) },
        R.id.job_scheduler_stop to { JobSchedulerService.unscheduleSimpleJob(this) },

        R.id.work_manager_start to { TimerWork.schedule(this, System.currentTimeMillis(), lifecycleScope) },
        R.id.work_manager_stop to { TimerWork.cancel(this) },

        R.id.alarm_manager_start to { Alarms.schedule(this) },
        R.id.alarm_manager_stop to { Alarms.cancel(this) }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        actionMapper.keys.forEach { id ->
            findViewById<View>(id).setOnClickListener { actionMapper[id]?.invoke() }
        }
    }

    override fun onStart() {
        super.onStart()

        boundServiceConnection.connect()
    }

    override fun onStop() {
        super.onStop()

        boundServiceConnection.disconnect()
    }

    @SuppressLint("InlinedApi")
    override fun onResume() {
        super.onResume()

        if (Notifications.requestPermission(this)) {
            notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    protected fun proceedNotificationPermission(isGranted: Boolean) {
        if (isGranted) {
            return
        }

        Toast.makeText(this, "Notifications are not allowed", Toast.LENGTH_LONG).show()
    }
}
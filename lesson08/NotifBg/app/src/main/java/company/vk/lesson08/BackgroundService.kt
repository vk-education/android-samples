package company.vk.lesson08

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.os.Process
import android.widget.Toast

class BackgroundService : Service() {
    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val command = intent?.getStringExtra(COMMAND)
        val data = intent?.getStringExtra(DATA)
        when (command) {
            null -> stopSelf()
            else -> handleCommand(command, data)
        }

        return START_NOT_STICKY
    }

    protected fun handleCommand(command: String, data: String?) {
        when(command) {
            COMMAND_SHOW_PID -> commandShowPid(data)
        }

        stopSelf()
    }

    protected fun commandShowPid(data: String?) {
        val pid = Process.myPid()
        Toast.makeText(this, "Service.pid=$pid, $data", Toast.LENGTH_LONG).show()
    }

    companion object {
        protected const val COMMAND = "COMMAND"
        protected const val DATA = "DATA"

        const val COMMAND_SHOW_PID = "COMMAND_SHOW_TOAST"

        fun sendCommand(context: Context, command: String, data: String?) {
            val intent = Intent(context, BackgroundService::class.java).apply {
                putExtra(COMMAND, command)
                putExtra(DATA, data)
            }

            context.startService(intent)
        }
    }
}
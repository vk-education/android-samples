package company.vk.lesson08

import android.app.Service
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Binder
import android.os.IBinder
import android.widget.Toast

class BoundService : Service() {
    protected val binder by lazy { Controller() }

    override fun onBind(intent: Intent) = binder

    inner class Controller : Binder() {
        fun showToast(message: String) {
            Toast.makeText(this@BoundService, message, Toast.LENGTH_LONG).show()
        }
    }

    class Connection(protected val context: Context): ServiceConnection {
        protected var isConnected = false
            set(value) {
                field = value
                Toast.makeText(context, "Connection.isConnected: $value", Toast.LENGTH_LONG).show()
            }

        protected var binder: Controller? = null

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            binder = service as? Controller
            isConnected = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            isConnected = false
        }

        fun connect() {
            val intent = Intent(context, BoundService::class.java)
            context.bindService(intent, this, Context.BIND_AUTO_CREATE)
        }

        fun disconnect() {
            context.unbindService(this)
            isConnected = false
        }

        fun controller(): Controller? {
            if (!isConnected) {
                Toast.makeText(context, "Not Connected!", Toast.LENGTH_LONG).show()
            }

            return binder
        }
    }

    companion object {
    }
}
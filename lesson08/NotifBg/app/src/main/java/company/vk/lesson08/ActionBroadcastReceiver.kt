package company.vk.lesson08

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class ActionBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val notificationId = intent.getIntExtra(NOTIFICATION_ID, -1)
        val command = intent.getStringExtra(COMMAND_KEY)
        when (command) {
            COMMAND_CANCEL -> Notifications.dismissNotification(context, notificationId)
        }
    }

    companion object {
        protected const val NOTIFICATION_ID = "NOTIFICATION_ID"
        protected const val COMMAND_KEY = "COMMAND_KEY"

        const val COMMAND_CANCEL = "COMMAND_CANCEL"

        fun newIntent(context: Context, notificationId: Int, command: String): Intent {
            return Intent(context, ActionBroadcastReceiver::class.java).apply {
                putExtra(NOTIFICATION_ID, notificationId)
                putExtra(COMMAND_KEY, command)
            }
        }
    }
}
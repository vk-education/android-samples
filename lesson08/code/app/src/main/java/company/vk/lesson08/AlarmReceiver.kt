package company.vk.lesson08

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

class AlarmReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val message = "at ${System.currentTimeMillis()}"
        Log.d("AlarmReceiver", message)
        Toast.makeText(context!!, "at ${System.currentTimeMillis()}", Toast.LENGTH_SHORT).show()
    }
}
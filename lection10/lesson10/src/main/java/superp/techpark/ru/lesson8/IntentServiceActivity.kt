package superp.techpark.ru.lesson8

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat

/**
 * Activity для демонстрации работы [DemoIntentService].
 */
class IntentServiceActivity : AppCompatActivity() {

    private val clickListener = View.OnClickListener { view ->
        val intent = Intent(this@IntentServiceActivity, DemoIntentService::class.java)
        intent.putExtra(DemoIntentService.EXTRA_STRING, "Ololo")
        val isForeground = foreground!!.isChecked
        intent.putExtra(DemoIntentService.EXTRA_FOREGROUND, isForeground)
        when (view.id) {
            R.id.to_lower -> intent.action = DemoIntentService.ACTION_TO_LOWER
            R.id.to_upper -> intent.action = DemoIntentService.ACTION_TO_UPPER
            else -> throw UnsupportedOperationException("Wrong view id " + view.id)
        }
        if (isForeground) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(intent)
            } else {
                startService(intent)
            }
        } else {
            startService(intent)
        }
    }


    private var foreground: SwitchCompat? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intent_service)
        foreground = findViewById(R.id.foreground)
        findViewById<View>(R.id.to_lower).setOnClickListener(clickListener)
        findViewById<View>(R.id.to_upper).setOnClickListener(clickListener)
    }
}
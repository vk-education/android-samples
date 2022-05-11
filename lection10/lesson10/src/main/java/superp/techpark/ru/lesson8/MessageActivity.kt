package superp.techpark.ru.lesson8

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MessageActivity : AppCompatActivity() {

    private var mMessageText: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message)
        mMessageText = findViewById(R.id.text_message)
        val message = intent.getStringExtra(EXTRA_TEXT)
        mMessageText?.text = message
    }

    companion object {
        const val EXTRA_TEXT = "extra_text"
    }
}
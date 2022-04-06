package com.example.lecture06

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sampleTextView = findViewById<TextView>(R.id.sample_text_view)

        Thread {
            val loadedText = loadText()
            this@MainActivity.runOnUiThread { sampleTextView.text = loadedText }
            sampleTextView.post { sampleTextView.text = loadedText }
            Handler(Looper.getMainLooper()).post { sampleTextView.text = loadedText }
        }.start()
    }

    private fun loadText(): String {
        Thread.sleep(1000)
        return "sample"
    }
}

package com.example.lecture06

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.net.URL

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        findViewById<TextView>(R.id.sample_text_view).text = loadText()

    }

    private fun loadText(): String {
        URL("https://www.google.com").openConnection().getInputStream()
        return "sample"
    }

}
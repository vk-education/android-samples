package com.example.lecture06

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        findViewById<View>(R.id.container).setOnClickListener {
            someHeavyWork()
        }

    }

    private fun someHeavyWork() {
        var a = 1
        while (true) a++
    }
}
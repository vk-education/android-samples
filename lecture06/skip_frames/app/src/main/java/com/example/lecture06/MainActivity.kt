package com.example.lecture06

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        someHeavyWork()
    }

    private fun someHeavyWork() {
        Thread.sleep(3000)
    }
}
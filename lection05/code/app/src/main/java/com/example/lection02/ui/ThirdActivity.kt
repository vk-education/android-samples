package com.example.lection02.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.lection02.R

class ThirdActivity() : AppCompatActivity() {

    private lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("Lection02", "Lection02 onCreate")
        setContentView(R.layout.third_activity)
        button = findViewById(R.id.button)
        button.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java).apply {
                putExtra("key", 1)
            })
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("Lection02", "Lection02 onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("Lection02", "Lection02 onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("Lection02", "Lection02 onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("Lection02", "Lection02 onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("Lection02", "Lection02 onDestroy")
    }
}
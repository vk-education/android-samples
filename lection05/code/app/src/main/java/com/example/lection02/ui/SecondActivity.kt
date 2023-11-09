package com.example.lection02.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.lection02.R

class SecondActivity() : AppCompatActivity() {

    private lateinit var btnCreate: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.setFragmentResultListener("result_key", this) { key, result ->
            Log.d("Lection02", "fragment result ${result.getString("key")}")
        }
        Log.d("Lection02", "Lection02 onCreate")
        setContentView(R.layout.second_activity)
        btnCreate = findViewById(R.id.button_add)
        btnCreate.setOnClickListener {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.container, FirstFragment(), "tag1")
                addToBackStack("tag1")
                commit()
            }
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
package com.example.lection02.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.lection02.R

class MainActivity : AppCompatActivity() {

    private lateinit var button: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val contract = registerForActivityResult(ResultContract()) { result ->
            Log.d("Lection02", "registerForActivityResult ${result}")
        }
        Log.d("Lection02", "Lection02 onCreate")
        setContentView(R.layout.main_activity)
        button = findViewById(R.id.button)
        button.setOnClickListener {
            startActivity(Intent(Intent.ACTION_CALL, Uri.parse("tel:+79991112233")))
            //contract.launch("привет!")
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Log.d("Lection02", "Lection02 onNewIntent + ${intent?.getIntExtra("key", 0)}")
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


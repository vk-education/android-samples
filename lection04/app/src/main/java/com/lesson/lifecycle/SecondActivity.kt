package com.lesson.lifecycle

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.lesson.lifecycle.model.InputData

class SecondActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.second_activity)
        val data = intent.getParcelableExtra<InputData>("key")
        Log.d("TEST", data?.field1.orEmpty())
        findViewById<Button>(R.id.button_action).setOnClickListener {
            setResult(Activity.RESULT_OK, Intent().apply { putExtra("result", 42) })
            finish()
//            val intent = Intent(this, ThirdActivity::class.java)
//            startActivity(intent)
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Log.d("TEST", "onNewIntent")
    }
}
package com.lesson.lifecycle

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.lesson.lifecycle.contract.FirstToSecondActivityContract

class StartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val register = registerForActivityResult(FirstToSecondActivityContract()) { result ->
            Log.d("TEST", "result ${result}")
        }

        //Log.d("TEST", "onCreate")
        //Log.d("TEST", "savedData: ${savedInstanceState?.getString("key").orEmpty()}")
        setContentView(R.layout.start_activity)
        findViewById<Button>(R.id.button_action).setOnClickListener {
            //register.launch(InputData(field1 = "test", field2 = 0))
            val intent = Intent(this, ThirdActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("TEST", "onStart")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("TEST", "onRestart")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("key", "test1")
        Log.d("TEST", "onSaveInstanceState")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.d("TEST", "onRestoreInstanceState")
    }

    override fun onResume() {
        super.onResume()
        Log.d("TEST", "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("TEST", "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("TEST", "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("TEST", "onDestroy")
    }
}
package com.mmmm

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.net.toUri
import com.example.myapplication.AbstractActivity
import com.example.myapplication.DroidParcel
import com.example.myapplication.MainActivity

class DummyActivity: AbstractActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val deeplink = intent.data!!
        val droid = intent.getParcelableExtra<DroidParcel>("droid")!!


        val view = TextView(this).apply {
            setBackgroundColor(Color.WHITE)
            setTextColor(Color.BLACK)
            text = droid.name

            setOnClickListener {
                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
            }
        }
        setContentView(view)

        val result = Intent().apply {
            data = "droid://${droid.name}/${droid.id}".toUri()
        }

        setResult(RESULT_OK, result)
        finish()
    }
}
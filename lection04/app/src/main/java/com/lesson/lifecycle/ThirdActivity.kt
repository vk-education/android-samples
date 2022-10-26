package com.lesson.lifecycle

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lesson.lifecycle.fragment.createFirstFragment

class ThirdActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.third_activity)

        val trasaction = supportFragmentManager.beginTransaction()
        trasaction.add(R.id.container, createFirstFragment("Hello"))
        trasaction.commit()
    }
}
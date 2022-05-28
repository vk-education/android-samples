package com.lionzxy.jetpackcompose

import android.os.Bundle
import androidx.fragment.app.FragmentActivity

class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment())
                .commit()
        }
    }
}

/**
 * 1. Как добавить Compose в Gradle
 * https://developer.android.com/jetpack/androidx/releases/compose-kotlin
 * 2. Как добавить Compose на экран
 * 3. Верстка
 */

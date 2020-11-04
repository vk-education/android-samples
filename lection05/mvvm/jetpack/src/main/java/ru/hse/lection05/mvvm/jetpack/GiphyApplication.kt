package ru.hse.lection05.mvvm.jetpack

import android.app.Application

class GiphyApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        Assembly.initialize(this)
    }
}
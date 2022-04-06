package com.example.lecture06

import android.app.Application

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        application = this
    }

    companion object {
        var application: Application? = null
    }
}
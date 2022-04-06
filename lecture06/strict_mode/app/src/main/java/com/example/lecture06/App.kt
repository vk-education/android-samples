package com.example.lecture06

import android.app.Application
import android.os.StrictMode

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(
                StrictMode.ThreadPolicy.Builder()
                    .detectDiskReads()
                    .detectDiskWrites()
                    .detectNetwork() // or .detectAll() for all detectable problems
                    .penaltyDeath()
                    .penaltyDialog()
                    .build()
            )
        }
    }
}
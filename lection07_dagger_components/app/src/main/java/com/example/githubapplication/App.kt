package com.example.githubapplication

import android.app.Application
import com.example.githubapplication.di.common.AppComponent
import com.example.githubapplication.di.common.AppModule
import com.example.githubapplication.di.common.DaggerAppComponent
import timber.log.Timber


class App : Application() {
    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent
            .builder()
            .appModule(AppModule(this))
            .build()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
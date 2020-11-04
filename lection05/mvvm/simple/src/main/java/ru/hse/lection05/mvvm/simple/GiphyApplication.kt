package ru.hse.lection05.mvvm.simple

import android.app.Application
import ru.hse.lection05.mvvm.simple.businesslayer.BaseProviderFactory
import ru.hse.lection05.mvvm.simple.businesslayer.ProviderFactory

class GiphyApplication: Application() {
    override fun onCreate() {
        super.onCreate()


        Assembly.initialize(this)
    }
}
package ru.hse.lection05.mvvm.simple

import android.content.Context
import ru.hse.lection05.mvvm.simple.businesslayer.BaseProviderFactory
import ru.hse.lection05.mvvm.simple.businesslayer.ProviderFactory

class Assembly {
    companion object {
        fun initialize(context: Context) {

            val baseFactory = BaseProviderFactory(context)
            ProviderFactory.add(baseFactory)
        }
    }
}
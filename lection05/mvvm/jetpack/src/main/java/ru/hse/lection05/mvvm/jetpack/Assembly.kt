package ru.hse.lection05.mvvm.jetpack

import android.content.Context
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.hse.lection05.mvvm.jetpack.modules.app
import ru.hse.lection05.mvvm.jetpack.modules.giphy

class Assembly {
    companion object {
        fun initialize(context: Context) {
            startKoin {
                androidContext(context)

                modules(
                    giphy
                    , app
                )
            }
        }
    }
}
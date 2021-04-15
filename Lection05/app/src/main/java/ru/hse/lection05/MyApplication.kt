package ru.hse.lection05

import android.app.Application
import io.paperdb.Paper
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.hse.lection05.businesslayer.repositories.PlaceRepositoryFactory
import ru.hse.lection05.businesslayer.repositories.RepositoryFactory

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()


        // Инициализация DB, для оффлайнового хранения
        Paper.init(this)


        // Пример ручного, самописного, DI
        RepositoryFactory.register(PlaceRepositoryFactory())



        // Инициализация KOIN-модулей, для DI
        val koinModules = listOf(
            commonModule
            , placeModule
            , weatherModule
        )

        startKoin {
            androidContext(this@MyApplication)
            modules(koinModules)
        }
    }
}
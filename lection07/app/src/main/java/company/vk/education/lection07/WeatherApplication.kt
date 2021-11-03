package company.vk.education.lection07

import android.app.Application
import company.vk.education.lection07.modules.baseModule
import company.vk.education.lection07.modules.openWeatherModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class WeatherApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@WeatherApplication)
            androidLogger()

            modules (
                baseModule,
                openWeatherModule
            )
        }
    }
}
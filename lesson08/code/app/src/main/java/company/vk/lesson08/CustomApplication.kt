package company.vk.lesson08

import android.app.Application

class CustomApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        Notifications.initialize(this)
    }
}

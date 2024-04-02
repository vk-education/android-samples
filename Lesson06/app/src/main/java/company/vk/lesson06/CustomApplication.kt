package company.vk.lesson06

import android.app.Application
import android.content.Context

class CustomApplication: Application() {
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)

        ServiceLocator.register(AndroidFactory(base!!))
        ServiceLocator.register(SimpleFactory())
    }
}
package company.vk.myapplication

import android.app.Application

class CatApplication : Application() {
	override fun onCreate() {
		super.onCreate()

		ServiceLocator.initialize(this)
	}
}
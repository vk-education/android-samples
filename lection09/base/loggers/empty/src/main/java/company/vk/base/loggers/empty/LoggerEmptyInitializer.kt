package company.vk.base.loggers.empty

import android.content.Context
import androidx.startup.Initializer
import company.vk.base.loggers.Logger

class LoggerEmptyInitializer: Initializer<Any> {
	override fun create(context: Context): Any {
		return Logger.apply {
			initialize(EmptyLogger())
		}
	}

	override fun dependencies(): MutableList<Class<out Initializer<*>>> = mutableListOf()
}
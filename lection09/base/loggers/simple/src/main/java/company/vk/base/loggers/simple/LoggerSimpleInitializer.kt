package company.vk.base.loggers.simple

import android.content.Context
import androidx.startup.Initializer
import company.vk.base.loggers.Logger

class LoggerSimpleInitializer: Initializer<Any> {
	override fun create(context: Context): Any {
		return Logger.apply {
			initialize(LogcatLogger())
		}
	}

	override fun dependencies(): MutableList<Class<out Initializer<*>>> = mutableListOf()
}
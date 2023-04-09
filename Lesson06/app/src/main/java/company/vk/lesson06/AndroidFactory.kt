package company.vk.Lesson06

import android.content.Context

class AndroidFactory(val context: Context): ServiceLocator.IFactory {
	override fun <T> inject(clazz: Class<T>): T? {
		val instance = when {
			clazz.isAssignableFrom(Context::class.java) -> context
			else -> null
		}

		return instance as? T
	}
}
package company.vk.base.films.simple

import android.content.Context
import androidx.startup.Initializer
import company.vk.common.ServiceLocator

class FilmsSimpleInitializer: Initializer<FilmSimpleFactory> {
	override fun create(context: Context): FilmSimpleFactory {
		val factory = FilmSimpleFactory()

		ServiceLocator.addFactory(factory)

		return factory
	}

	override fun dependencies(): MutableList<Class<out Initializer<*>>> = mutableListOf()
}
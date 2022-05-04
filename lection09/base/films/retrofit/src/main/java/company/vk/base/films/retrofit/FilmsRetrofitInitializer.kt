package company.vk.base.films.retrofit

import android.content.Context
import androidx.startup.Initializer
import company.vk.common.ServiceLocator

class FilmsRetrofitInitializer: Initializer<AbstractFilmRetrofitFactory> {
	override fun create(context: Context): AbstractFilmRetrofitFactory {
		val factory = FilmRetrofitFactory()

		ServiceLocator.addFactory(factory)

		return factory
	}

	override fun dependencies(): MutableList<Class<out Initializer<*>>> = mutableListOf()
}
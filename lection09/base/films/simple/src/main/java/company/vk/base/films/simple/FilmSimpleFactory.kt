package company.vk.base.films.simple

import company.vk.base.films.businesslayer.FilmProvider
import company.vk.base.films.businesslayer.IFilmProvider
import company.vk.base.films.datalayer.IApiAccessor
import company.vk.base.films.simple.datalayer.SimpleApiAccessor
import company.vk.common.ServiceLocator

class FilmSimpleFactory: ServiceLocator.IFactory {
	override fun create(clazz: Class<*>): Any? {
		return when {
			clazz.isAssignableFrom(IFilmProvider::class.java) -> FilmProvider()
			clazz.isAssignableFrom(IApiAccessor::class.java) -> SimpleApiAccessor("https://ghibliapi.herokuapp.com/")
			else -> null
		}
	}
}
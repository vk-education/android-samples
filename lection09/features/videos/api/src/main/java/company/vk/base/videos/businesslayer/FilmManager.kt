package company.vk.base.videos.businesslayer

import company.vk.base.films.businesslayer.IFilmProvider
import company.vk.base.films.objects.Film
import company.vk.common.ServiceLocator.inject

class FilmManager {
	protected val provider by inject<IFilmProvider>()


	fun filmList(callback: (result: List<Film>?, error: Throwable?) -> Unit) = provider.filmList(callback)
}
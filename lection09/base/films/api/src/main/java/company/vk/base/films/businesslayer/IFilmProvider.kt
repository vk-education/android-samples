package company.vk.base.films.businesslayer

import company.vk.base.films.objects.Film

interface IFilmProvider {
	fun filmList(callback: (result: List<Film>?, error: Throwable?) -> Unit)
}
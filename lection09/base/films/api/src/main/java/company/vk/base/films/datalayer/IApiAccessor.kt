package company.vk.base.films.datalayer

import company.vk.base.films.objects.Film

interface IApiAccessor {
	suspend fun filmList(): List<Film>
}
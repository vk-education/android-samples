package company.vk.base.films.retrofit.datalayer

import company.vk.base.films.datalayer.IApiAccessor
import company.vk.base.films.retrofit.objects.GsonFilm
import retrofit2.http.GET

interface IApiRetrofitAccessor: IApiAccessor {
	@GET("films")
	override suspend fun filmList(): List<GsonFilm>
}
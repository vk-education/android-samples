package company.vk.education.lection07.datalayer.accessors

import company.vk.education.lection07.objects.openweather.OpenWeatherList
import company.vk.education.lection07.objects.openweather.OpenWeatherPlace
import retrofit2.http.GET
import retrofit2.http.Query

interface IOpenWeatherAccessor {
    @GET("find")
    suspend fun find(@Query("q") q: String): OpenWeatherList<OpenWeatherPlace>
}
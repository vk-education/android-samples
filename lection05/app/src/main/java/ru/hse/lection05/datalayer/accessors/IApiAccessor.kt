package ru.hse.lection05.datalayer.accessors

import retrofit2.http.GET
import retrofit2.http.Query
import ru.hse.lection05.objects.ApiListResult
import ru.hse.lection05.objects.Place
import ru.hse.lection05.objects.WeatherData

interface IApiAccessor {
    @GET("find")
    suspend fun find(@Query("q") query: String): ApiListResult<Place>

    @GET("weather")
    suspend fun weather(@Query("id") id: Long): WeatherData
}
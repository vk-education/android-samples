package ru.hse.lection05.api.giphy.datalayer

import retrofit2.http.GET
import retrofit2.http.Query
import ru.hse.lection05.api.giphy.objects.ListResult

interface IDataAccessor {
    companion object {
        const val HOST = "https://giphy.p.rapidapi.com/v1/"
    }


    @GET("gifs/search")
    suspend fun search(@Query("limit") limit: Int? = null, @Query("q") q: String): ListResult

    @GET("gifs/trending")
    suspend fun trending(@Query("limit") limit: Int? = null): ListResult
}
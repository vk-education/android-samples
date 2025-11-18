package com.vk.lection06_concurrency_network

import retrofit2.Response
import retrofit2.http.GET

interface JokeApi {
    @GET("jokes/random")
    suspend fun joke(): Response<Joke>
}

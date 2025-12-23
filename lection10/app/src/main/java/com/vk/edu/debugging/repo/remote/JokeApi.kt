package com.vk.edu.debugging.repo.remote

import com.vk.edu.debugging.repo.JokeDto
import retrofit2.Response
import retrofit2.http.GET

interface JokeApi {
    @GET("jokes/random")
    suspend fun joke(): Response<JokeDto>
}
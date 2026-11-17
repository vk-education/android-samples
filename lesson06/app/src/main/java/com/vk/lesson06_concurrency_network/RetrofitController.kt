package com.vk.lesson06_concurrency_network

import retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit


class RetrofitController(api: String) : RequestController {

    private val json = Json { ignoreUnknownKeys = true }

    private val retrofit = Retrofit.Builder()
        .baseUrl(api)
        .addConverterFactory(
            json.asConverterFactory(
                "application/json; charset=UTF8".toMediaType()
            )
        )
        .build()

    private val jokeApi = retrofit.create(JokeApi::class.java)

    override suspend fun requestJoke(): Result {
        val response = jokeApi.joke()
        return if (response.isSuccessful) {
            response.body()?.let {
                Result.Ok(it)
            } ?: Result.Error("Empty joke")
        } else {
            Result.Error(response.code().toString())
        }
    }
}

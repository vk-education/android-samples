package com.vk.edu.debugging.repo.remote

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.vk.edu.debugging.repo.JokeDto
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

@OptIn(ExperimentalSerializationApi::class)
class RemoteSource {
    private val json = Json { ignoreUnknownKeys = true }
    private val jsonConverter = json.asConverterFactory(
        "application/json; charset=UTF8".toMediaType()
    )
    private val retrofit = Retrofit.Builder()
        .baseUrl(API)
        .addConverterFactory(jsonConverter)
        .build()

    private val jokeApi = retrofit.create(JokeApi::class.java)

    suspend fun requestJoke(): RequestResult {
        val response = jokeApi.joke()
        return if (response.isSuccessful) {
            response.body()?.let {
                RequestResult.Ok(it)
            } ?: RequestResult.Error("Empty joke")
        } else {
            RequestResult.Error(response.code().toString())
        }
    }

    sealed interface RequestResult {
        data class Ok(val joke: JokeDto) : RequestResult
        data class Error(val error: String) : RequestResult
    }


    companion object {
        private const val API = "https://api.chucknorris.io"

    }
}
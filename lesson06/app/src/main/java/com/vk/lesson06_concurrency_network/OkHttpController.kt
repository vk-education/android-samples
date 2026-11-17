package com.vk.lesson06_concurrency_network

import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import kotlin.coroutines.resume


class OkHttpController(private val api: String) : RequestController {
    private val json by lazy {
        Json{
            ignoreUnknownKeys = true
        }
    }

    override suspend fun requestJoke(): Result {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("$api/jokes/random")
            .build()

        return suspendCancellableCoroutine<Result> { continuation ->
            client
                .newCall(request)
                .execute()
                .use { response ->
                    if (response.code != 200) {
                        continuation.resume(
                            Result.Error(
                                "Request troubles: ${response.code}:${response.message}"
                            )
                        )
                    } else {
                        try {
                            val joke: Joke = json.decodeFromString<Joke>(response.body.string())
                            continuation.resume(Result.Ok(joke))
                        } catch (e: SerializationException) {
                            continuation.resume(Result.Error("${e.message}"))
                        }
                    }
                }
        }
    }
}

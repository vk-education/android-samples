package com.example.lecture06

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface UserApi {

    @Headers("Cache-Control: max-age=640000")
    @GET("/users/{id}")
    suspend fun getUser(@Path("id") userId: Int): UserData
}

@JsonClass(generateAdapter = true)
data class UserData(
    @field:Json(name = "id") val id: Int,
    @field:Json(name = "email") val email: String,
    @field:Json(name = "name") val name: String,
    @field:Json(name = "username") val username: String
)

fun main() {

    val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    val client = OkHttpClient.Builder()
        .addInterceptor(interceptor = loggingInterceptor)
        .addNetworkInterceptor(interceptor = loggingInterceptor)
        .build()


    val retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl("https://jsonplaceholder.typicode.com")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    val userApi = retrofit.create(UserApi::class.java)

    runBlocking {
        val adapter = Moshi.Builder().build().adapter(UserData::class.java)
        println(adapter.toJson(userApi.getUser(userId = 1)))
    }
}

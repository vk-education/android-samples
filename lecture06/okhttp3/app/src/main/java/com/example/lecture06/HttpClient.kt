package com.example.lecture06

import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.logging.HttpLoggingInterceptor
import java.io.IOException


fun main() {

    class SimpleBearerInterceptor(private val accessToken: String) : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response =
            chain.proceed(
                chain.request()
                    .newBuilder()
                    .addHeader("Authorization", "Bearer $accessToken")
                    .build()
            )
    }

    class CacheControlInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val response = chain.proceed(chain.request())
            return response.newBuilder()
                .header("Cache-Control", "max-age=100")
                .build();
        }
    }


    val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    val client = OkHttpClient.Builder()
        .addInterceptor(interceptor = loggingInterceptor)
        .addInterceptor(interceptor = SimpleBearerInterceptor(accessToken = "code"))
        .addNetworkInterceptor(interceptor = loggingInterceptor)
        .addNetworkInterceptor(CacheControlInterceptor())
        .build()

    val httpUrl = "http://publicobject.com/helloworld.txt".toHttpUrl()
        .newBuilder()
        .addQueryParameter(name = "simple", value = "query")
        .build()

    val request = Request.Builder()
        .url(url = "http://publicobject.com/helloworld.txt")
        .addHeader(name = "Accept", value = "application/json")
        .addHeader(name = "Content-Type", value = "application/json;charset=utf-8")
        .build()

    client.newCall(request = request).execute().use { response ->
        response.body!!.charStream().forEachLine(::println)
    }

    client.newCall(request = request).enqueue(responseCallback = object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            //
        }

        override fun onResponse(call: Call, response: Response) {
            //
        }
    })


}






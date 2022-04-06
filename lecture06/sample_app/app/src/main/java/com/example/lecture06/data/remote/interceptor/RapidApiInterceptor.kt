package com.example.lecture06.data.remote.interceptor

import com.example.lecture06.data.remote.Constants.API_KEY
import com.example.lecture06.data.remote.Constants.BASE_URL
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.Interceptor
import okhttp3.Response

class RapidApiInterceptor : Interceptor {

    private val host = BASE_URL.toHttpUrl().host

    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request().newBuilder()
            .addHeader(HOST_NAME, host)
            .addHeader(KEY_NAME, API_KEY)
            .build()

        return chain.proceed(request)
    }

    companion object {
        private const val HOST_NAME = "X-RapidAPI-Host"
        private const val KEY_NAME = "X-RapidAPI-Key"
    }
}
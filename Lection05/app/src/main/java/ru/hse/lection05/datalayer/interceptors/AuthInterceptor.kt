package ru.hse.lection05.datalayer.interceptors

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val newUrl = originalRequest.url
                .newBuilder()
                .addQueryParameter("appid", API_KEY)
                .build()

        val newRequest = originalRequest
                .newBuilder()
                .url(newUrl)
                .build()

        return chain.proceed(newRequest)
    }


    companion object {
        const val API_KEY = "<INSERT YOUR API KEY>"
    }
}
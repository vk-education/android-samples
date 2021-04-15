package ru.hse.lection05.datalayer.interceptors

import okhttp3.Interceptor
import okhttp3.Response

class PreferencesInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val newUrl = originalRequest.url
                .newBuilder()
                .addQueryParameter("units", "metric")
                .addQueryParameter("lang", "ru")
                .build()

        val newRequest = originalRequest
                .newBuilder()
                .url(newUrl)
                .build()

        return chain.proceed(newRequest)
    }
}
package com.example.lecture06.data.remote.interceptor

import android.util.Log
import com.example.lecture06.App.Companion.application
import com.example.lecture06.utils.NetworkUtils
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import java.util.concurrent.TimeUnit

class CacheOfflineInterceptor : Interceptor {


    override fun intercept(chain: Interceptor.Chain): Response {
        val networkAvailable = NetworkUtils.isNetworkAvailable(application?.applicationContext!!)

        val request = if (!networkAvailable) {
            chain.request().newBuilder()
                .cacheControl(
                    CacheControl.Builder()
                        .onlyIfCached()
                        .maxStale(5, TimeUnit.MINUTES)
                        .build()
                )
                .removeHeader("Pragma")
                .build()
        } else {
            chain.request()
        }
        val response = chain.proceed(request)

        Log.d("Cache Response", "response: $response")
        Log.d("Cache Response", "cache response:    ${response.cacheResponse}")
        Log.d("Cache Response", "network response:  ${response.networkResponse}")

        return response
    }
}

package com.example.lecture06.data.remote.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class CookieInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        return chain.proceed(chain.request()).newBuilder()
            .addHeader("Set-Cookie", "key=value")
            .build()
    }


}
package ru.example.myapplication.datalayer

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        // для работы запросов необходимо вставить данные из https://rapidapi.com/giphy/api/giphy


        val url = chain.request().url.newBuilder()
            .addQueryParameter(QUERY_API_KEY, <Вставить сюда api_key с сайта rapidapi (ссылка сверху)>)
            .build()

        val request = chain.request().newBuilder()
            .url(url)
            .header(HEADER_RAPIDAPI_HOST, "giphy.p.rapidapi.com")
            .header(HEADER_RAPIDAPI_KEY, <Вставить сюда api_key с сайта rapidapi (ссылка сверху)>)
            .build()

        return chain.proceed(request)
    }


    companion object {
        const val QUERY_API_KEY = "api_key"
        const val HEADER_RAPIDAPI_HOST = "x-rapidapi-host"
        const val HEADER_RAPIDAPI_KEY = "x-rapidapi-key"
    }
}
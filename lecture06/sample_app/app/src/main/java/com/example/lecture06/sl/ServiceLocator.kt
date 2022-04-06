package com.example.lecture06.sl

import com.example.lecture06.App.Companion.application
import com.example.lecture06.coroutines.DefaultCoroutineDispatchers
import com.example.lecture06.data.CovidRepositoryImpl
import com.example.lecture06.data.converter.CountriesResponseToCountriesConverter
import com.example.lecture06.data.converter.CountryHistoryResponseToCountryHistoryConverter
import com.example.lecture06.data.remote.Constants.BASE_URL
import com.example.lecture06.data.remote.CovidApi
import com.example.lecture06.data.remote.interceptor.CacheOfflineInterceptor
import com.example.lecture06.data.remote.interceptor.CacheOnlineInterceptor
import com.example.lecture06.data.remote.interceptor.CookieInterceptor
import com.example.lecture06.data.remote.interceptor.RapidApiInterceptor
import com.example.lecture06.domain.GetCountryWithCovidHistoryUseCaseImpl
import com.example.lecture06.domain.api.CovidRepository
import com.example.lecture06.domain.api.GetCountryWithCovidHistoryUseCase
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object ServiceLocator {

    private val covidApi: CovidApi by lazy(::createHttpClient)

    private val covidRepository: CovidRepository by lazy {
        CovidRepositoryImpl(
            DefaultCoroutineDispatchers(), covidApi,
            CountryHistoryResponseToCountryHistoryConverter(),
            CountriesResponseToCountriesConverter()
        )
    }

    val countriesInteractor: GetCountryWithCovidHistoryUseCase by lazy {
        GetCountryWithCovidHistoryUseCaseImpl(covidRepository)
    }

    private fun createHttpClient(): CovidApi {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(RapidApiInterceptor())
            .addNetworkInterceptor(loggingInterceptor)
            .addNetworkInterceptor(CookieInterceptor())
            .addNetworkInterceptor(CacheOnlineInterceptor())
            .addInterceptor(CacheOfflineInterceptor())
            .cookieJar(InMemoryCookieJar())
            .cache(
                Cache(
                    application?.cacheDir!!,
                    10 * 1024 * 1024// 10mb
                )
            )
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        return retrofit.create(CovidApi::class.java)

    }

    private class InMemoryCookieJar : CookieJar {
        private val urlToCookies = mutableMapOf<String, List<Cookie>>()

        override fun loadForRequest(url: HttpUrl): List<Cookie> =
            urlToCookies[url.host].orEmpty()

        override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
            urlToCookies[url.host] = cookies
        }

    }

}
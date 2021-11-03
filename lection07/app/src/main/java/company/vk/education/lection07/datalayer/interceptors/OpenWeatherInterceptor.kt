package company.vk.education.lection07.datalayer.interceptors

import okhttp3.Interceptor
import okhttp3.Response

class OpenWeatherInterceptor(val apiKey: String): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val newUrl = request.url.newBuilder()
            .addQueryParameter(APPID, apiKey)
            .addQueryParameter(LANG, LANG_RU)
            .addQueryParameter(UNITS, UNITS_METRIC)
            .build()

        val newRequest = request.newBuilder()
            .url(newUrl)
            .build()

        return chain.proceed(newRequest)
    }


    companion object {
        const val APPID = "appid"

        const val LANG = "lang"
        const val LANG_RU = "ru"

        const val UNITS = "units"
        const val UNITS_METRIC = "metric"
    }
}
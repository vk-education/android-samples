package company.vk.education.lection07.modules

import com.google.gson.GsonBuilder
import company.vk.education.lection07.businesslayer.providers.IPlaceProvider
import company.vk.education.lection07.businesslayer.providers.OpenWeatherPlaceProvider
import company.vk.education.lection07.datalayer.accessors.IOpenWeatherAccessor
import company.vk.education.lection07.datalayer.interceptors.OpenWeatherInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val openWeatherModule = module() {
    factory<IPlaceProvider> { OpenWeatherPlaceProvider(get()) }

    factory<IOpenWeatherAccessor> {
        val openWeatherUrl = "https://api.openweathermap.org/data/2.5/"
        val openWeatherKey = "<SET HERE OPEN_WEATHER_API_KEY>"

        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(OpenWeatherInterceptor(openWeatherKey))
            .addNetworkInterceptor(loggingInterceptor)
            .build()


        val gson = GsonBuilder()
            .create()

        val gsonConverter = GsonConverterFactory.create(gson)


        Retrofit.Builder()
            .baseUrl(openWeatherUrl)
            .addConverterFactory(gsonConverter)
            .client(client)
            .build()
            .create(IOpenWeatherAccessor::class.java)
    }
}
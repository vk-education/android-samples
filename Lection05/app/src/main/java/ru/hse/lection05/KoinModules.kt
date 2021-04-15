package ru.hse.lection05

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.hse.lection05.businesslayer.repositories.IPlaceRepository
import ru.hse.lection05.businesslayer.repositories.IWeatherRepository
import ru.hse.lection05.businesslayer.repositories.PlaceRepository
import ru.hse.lection05.businesslayer.repositories.WeatherRepository
import ru.hse.lection05.datalayer.accessors.IApiAccessor
import ru.hse.lection05.datalayer.accessors.IOfflineAccessor
import ru.hse.lection05.datalayer.accessors.PaperOfflineAccessor
import ru.hse.lection05.datalayer.interceptors.AuthInterceptor
import ru.hse.lection05.datalayer.interceptors.PreferencesInterceptor
import ru.hse.lection05.objects.Place

val commonModule = module() {
    single<IApiAccessor> {
        // Логгер запросов. Его желательно подключать только во время разработки и не оставлять в релизных сборках
        val logging = HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor())
            .addInterceptor(PreferencesInterceptor())
            .addNetworkInterceptor(logging)
            .build()

        val gson = GsonBuilder()
            .create()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        retrofit.create(IApiAccessor::class.java)
    }


    factory<IOfflineAccessor<Place>> { (name: String) ->
        PaperOfflineAccessor(name)
    }
}


val placeModule = module {
    single<IPlaceRepository> {
        PlaceRepository(get(), get { parametersOf("Place") })
    }
}


val weatherModule = module {
    single<IWeatherRepository> {
        WeatherRepository(get())
    }
}

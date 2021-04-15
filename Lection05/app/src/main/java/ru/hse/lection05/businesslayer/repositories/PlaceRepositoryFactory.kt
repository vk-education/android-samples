package ru.hse.lection05.businesslayer.repositories

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.hse.lection05.datalayer.accessors.IApiAccessor
import ru.hse.lection05.datalayer.accessors.PaperOfflineAccessor
import ru.hse.lection05.datalayer.interceptors.AuthInterceptor
import ru.hse.lection05.datalayer.interceptors.PreferencesInterceptor
import ru.hse.lection05.objects.Place

class PlaceRepositoryFactory: RepositoryFactory.IFactory {
    private val api by lazy { createApi() }


    override fun <T> acqure(clazz: Class<T>): T? {
        return when(clazz) {
            IPlaceRepository::class.java -> createPlaceProvider() as T
            else -> null
        }
    }

    private fun createApi(): IApiAccessor {
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

        return retrofit.create(IApiAccessor::class.java)
    }

    private fun createPlaceProvider(): IPlaceRepository {
        val offlineAccessor = PaperOfflineAccessor<Place>("Place")
        return PlaceRepository(api, offlineAccessor)

        // Или, если хотим на все методы возвращать ошибки:
//        return OnlyErrorPlaceRepository()
    }
}
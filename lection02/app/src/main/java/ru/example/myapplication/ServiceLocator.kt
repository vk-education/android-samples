package ru.example.myapplication

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.example.myapplication.datalayer.AuthInterceptor
import ru.example.myapplication.datalayer.IGiphyApi

object ServiceLocator {
    private val instances = mutableMapOf<Any, Any>()


    fun <INSTANCE> inject(clazz: Class<INSTANCE>): INSTANCE {
        return findOrCreate(clazz) as INSTANCE
    }

    inline fun <reified INSTANCE> inject() = lazy {
        val clazz = INSTANCE::class.java
        inject(clazz)
    }


    private fun findOrCreate(clazz: Class<*>): Any {
        instances[clazz]?.let {
            return it
        }

        val instance = create(clazz)!!
        instances[clazz] = instance

        return instance
    }

    private fun create(clazz: Class<*>): Any? {
        return when {
            clazz.isAssignableFrom(IGiphyApi::class.java) -> createGiphyApi()
            else -> clazz.newInstance()
        }
    }

    private fun createGiphyApi(): IGiphyApi {
        val authInterceptor = AuthInterceptor()

        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addNetworkInterceptor(loggingInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://giphy.p.rapidapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        val api = retrofit.create(IGiphyApi::class.java)

        return api
    }
}
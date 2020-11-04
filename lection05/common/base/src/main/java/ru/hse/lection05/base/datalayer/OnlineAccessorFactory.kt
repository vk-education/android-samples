package ru.hse.lection05.base.datalayer

import android.content.Context
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.hse.lection05.api.giphy.datalayer.AuthorizationInterceptor
import ru.hse.lection05.api.giphy.datalayer.IDataAccessor
import ru.hse.lection05.base.R

class OnlineAccessorFactory(val context: Context) {
    fun accessor(tag: String): IDataAccessor {
        val key = context.getString(R.string.api_key)
        val interceptor = AuthorizationInterceptor(key)

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        val gson = GsonBuilder()
            .create()

        val factory = GsonConverterFactory
            .create(gson)

        return Retrofit.Builder()
            .baseUrl(IDataAccessor.HOST)
            .client(client)
            .addConverterFactory(factory)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
            .create(IDataAccessor::class.java)
    }
}
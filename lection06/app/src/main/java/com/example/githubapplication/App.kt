package com.example.githubapplication

import android.app.Application
import android.os.StrictMode
import androidx.room.Room
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber


class App : Application() {
    companion object {
        lateinit var retrofit: Retrofit
        lateinit var db: AppDatabase
    }

    override fun onCreate() {
        super.onCreate()
        retrofit = getClient()
        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database-name.db"
        ).build()


        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    fun getClient(): Retrofit {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
        return Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
    }
}
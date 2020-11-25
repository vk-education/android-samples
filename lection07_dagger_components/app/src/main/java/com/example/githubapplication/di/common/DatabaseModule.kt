package com.example.githubapplication.di.common

import android.content.Context
import androidx.room.Room
import com.example.githubapplication.model.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {
    @Singleton
    @Provides
    fun provideDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java, "database-name.db"
        ).build()
    }
}
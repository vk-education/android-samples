package ru.hse.lection10.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.hse.lection10.ItemFragment
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DummyModule {
    @Provides
    @Singleton
    fun provideDummyProvider(cache: ItemFragment.IDummyOfflineAccessor): ItemFragment.DummyProvider {
        return ItemFragment.DummyProvider(cache)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): ItemFragment.AbstractDatabase {
        return Room.databaseBuilder(context, ItemFragment.AbstractDatabase::class.java, "database-name").build()
    }

    @Provides
    fun provideCache(database: ItemFragment.AbstractDatabase): ItemFragment.IDummyOfflineAccessor {
        return database.dummyOfflineAccessor()
    }
}
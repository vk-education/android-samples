package com.example.githubapplication.di

import android.content.Context
import com.example.githubapplication.presenter.main.MainPresenter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule (private val context: Context) {

    @Singleton
    @Provides
    fun provideContext() = context
}
package com.example.githubapplication.di

import com.example.githubapplication.presenter.main.MainPresenter
import com.example.githubapplication.view.main.MainActivity
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppModule::class, NetworkModule::class, DatabaseModule::class])
@Singleton
interface AppComponent {
    fun inject(activity: MainPresenter)
}
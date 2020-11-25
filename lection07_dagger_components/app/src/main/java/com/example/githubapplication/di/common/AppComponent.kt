package com.example.githubapplication.di.common

import com.example.githubapplication.di.main.GithubComponent
import com.example.githubapplication.di.main.GithubModule
import com.example.githubapplication.presenter.main.MainPresenter
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppModule::class, NetworkModule::class, DatabaseModule::class])
@Singleton
interface AppComponent {
    fun plus(module: GithubModule): GithubComponent
}
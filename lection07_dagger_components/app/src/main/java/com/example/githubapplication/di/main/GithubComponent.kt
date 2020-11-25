package com.example.githubapplication.di.main

import com.example.githubapplication.di.common.AppComponent
import com.example.githubapplication.presenter.main.MainPresenter
import dagger.Component
import dagger.Subcomponent
import javax.inject.Singleton

@Subcomponent(
    modules = [GithubModule::class]
)
@GithubScope
interface GithubComponent {
    fun inject(activity: MainPresenter)
}
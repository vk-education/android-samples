package com.lionzxy.githubapplication.rxjava

import com.lionzxy.githubapplication.model.GithubApi
import com.lionzxy.githubapplication.model.GithubRepo
import com.lionzxy.githubapplication.model.GithubUser
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object GithubDataStoreRxJava {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.github.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val githubApi = retrofit.create(GithubApi::class.java)

    fun getRepositories(): Single<List<GithubRepo>> {
        return Single.fromCallable {
            githubApi.getUserRepos("LionZXY").execute().body() ?: error("Empty repos")
        }.subscribeOn(Schedulers.io())
    }

    fun getUser(): Single<GithubUser> {
        return Single.fromCallable {
            githubApi.getUser("LionZXY").execute().body() ?: error("Empty user")
        }.subscribeOn(Schedulers.io())
    }
}

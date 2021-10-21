package com.lionzxy.githubapplication.coroutines

import com.lionzxy.githubapplication.model.GithubApi
import com.lionzxy.githubapplication.model.GithubRepo
import com.lionzxy.githubapplication.model.GithubUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object GithubDataStoreCoroutines {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.github.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val githubApi = retrofit.create(GithubApi::class.java)

    suspend fun getRepositories(nickname: String): List<GithubRepo> = withContext(Dispatchers.IO) {
        return@withContext githubApi.getUserRepos(nickname).execute().body()
            ?: error("No repositories")
    }

    suspend fun getUser(nickname: String): GithubUser = withContext(Dispatchers.IO) {
        return@withContext githubApi.getUser("LionZXY").execute().body()
            ?: error("Not found user")
    }
}

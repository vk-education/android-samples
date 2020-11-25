package com.example.githubapplication.repository.network

import com.example.githubapplication.GithubApi
import com.example.githubapplication.repository.db.DBRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NetworkRepository(
    private val githubApi: GithubApi,
    private val dbRepository: DBRepository
) {
    suspend fun getRepoForUser(username: String) = withContext(Dispatchers.IO) {
        val repo = githubApi.getUserRepos(username, perPage = 1).first()
        dbRepository.saveLastAnswer(repo)
        return@withContext repo
    }

}
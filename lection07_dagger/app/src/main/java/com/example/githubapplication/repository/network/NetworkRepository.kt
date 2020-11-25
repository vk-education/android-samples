package com.example.githubapplication.repository.network

import com.example.githubapplication.model.GithubApi
import com.example.githubapplication.repository.db.DBRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class NetworkRepository @Inject constructor(
    private val githubApi: GithubApi,
    private val dbRepository: DBRepository
) {
    suspend fun getRepoForUser(username: String) = withContext(Dispatchers.IO) {
        val repo = githubApi.getUserRepos(username, perPage = 1).first()
        dbRepository.saveLastAnswer(repo)
        return@withContext repo
    }
}
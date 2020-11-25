package com.example.githubapplication.repository.db

import com.example.githubapplication.model.AppDatabase
import com.example.githubapplication.model.GitHubRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DBRepository @Inject constructor(
    private val db: AppDatabase
) {
    suspend fun saveLastAnswer(answer: GitHubRepo) = withContext(Dispatchers.IO) {
        db.githubDao().insert(answer)
    }

    suspend fun getLastAnswer() = withContext(Dispatchers.IO) {
        db.githubDao().getAll().firstOrNull()
    }
}
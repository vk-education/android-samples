package com.example.githubapplication.repository.db

import com.example.githubapplication.App
import com.example.githubapplication.AppDatabase
import com.example.githubapplication.GitHubRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DBRepository(
    private val db: AppDatabase
) {
    suspend fun saveLastAnswer(answer: GitHubRepo) = withContext(Dispatchers.IO) {
        db.githubDao().insert(answer)
    }

    suspend fun getLastAnswer() = withContext(Dispatchers.IO) {
        db.githubDao().getAll().firstOrNull()
    }
}
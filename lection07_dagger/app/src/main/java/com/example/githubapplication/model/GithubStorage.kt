package com.example.githubapplication.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.githubapplication.model.GitHubRepo

@Dao
interface GithubStorage {
    @Query("SELECT * FROM repos")
    suspend fun getAll(): List<GitHubRepo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg gitHubRepo: GitHubRepo)
}
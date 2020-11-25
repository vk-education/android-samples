package com.example.githubapplication

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface GithubStorage {
    @Query("SELECT * FROM repos")
    suspend fun getAll(): List<GitHubRepo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg gitHubRepo: GitHubRepo)
}
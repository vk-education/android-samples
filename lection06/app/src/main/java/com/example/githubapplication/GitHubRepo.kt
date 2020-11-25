package com.example.githubapplication

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "repos")
data class GitHubRepo(
    @PrimaryKey
    @SerializedName("id")
    var id: Long,
    @SerializedName("name")
    var name: String
)
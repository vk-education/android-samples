package com.lionzxy.githubapplication.model

import com.google.gson.annotations.SerializedName

data class GithubRepo(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)

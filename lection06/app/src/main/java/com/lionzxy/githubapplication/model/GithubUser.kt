package com.lionzxy.githubapplication.model

import com.google.gson.annotations.SerializedName

data class GithubUser(
    @SerializedName("bio")
    val bio: String
)

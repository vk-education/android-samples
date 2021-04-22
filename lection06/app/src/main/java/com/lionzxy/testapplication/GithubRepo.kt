package com.lionzxy.testapplication

import com.google.gson.annotations.SerializedName

data class GithubRepo(
    @SerializedName("id")
    val id: Int
)
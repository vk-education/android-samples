package com.lionzxy.testapplication

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubApi {
    @GET("users/{user}/repos")
    fun getUserRepos(
        @Path("user") user: String
    ): Call<List<GithubRepo>>
}
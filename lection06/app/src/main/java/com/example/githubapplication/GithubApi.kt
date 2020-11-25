package com.example.githubapplication

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApi {
    @GET("users/{user}/repos")
    suspend fun getUserRepos(
        @Path("user") user: String,
        @Query("per_page") perPage: Int = 100,
        @Query("page") page: Int = 1
    ): List<GitHubRepo>
}
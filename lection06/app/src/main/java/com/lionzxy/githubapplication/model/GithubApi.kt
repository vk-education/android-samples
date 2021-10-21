package com.lionzxy.githubapplication.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubApi {
    @GET("users/{user}/repos")
    fun getUserRepos(
        @Path("user") nickname: String
    ): Call<List<GithubRepo>>

    @GET("users/{user}")
    fun getUser(
        @Path("user") nickname: String
    ): Call<GithubUser>
}

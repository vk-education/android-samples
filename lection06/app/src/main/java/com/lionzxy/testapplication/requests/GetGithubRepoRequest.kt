package com.lionzxy.testapplication.requests

import com.lionzxy.testapplication.GithubApi
import com.lionzxy.testapplication.GithubRepo
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.ref.WeakReference

class GetGithubRepoRequest : Runnable {
    private var listener = WeakReference<IResultReciever?>(null)

    companion object {
        fun getRepos(): List<GithubRepo> {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val githubApi = retrofit.create(GithubApi::class.java)
            val repos = githubApi.getUserRepos("LionZXY").execute()
            return repos.body()!!
        }
    }

    override fun run() {


        listener.get()?.onRepoAccept(getRepos())
    }

    fun subscribe(inputListener: IResultReciever) {
        listener = WeakReference(inputListener)
    }

    fun unsubscribe() {
        listener.clear()
    }

}
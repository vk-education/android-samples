package com.lionzxy.testapplication.requests

import com.lionzxy.testapplication.GithubRepo

interface IResultReciever {
    fun onRepoAccept(list: List<GithubRepo>)
}
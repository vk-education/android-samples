package com.vk.edu.debugging

import android.app.Application
import com.vk.edu.debugging.repo.JokeRepository
import com.vk.edu.debugging.repo.local.LocalSource
import com.vk.edu.debugging.repo.remote.RemoteSource
import com.vk.edu.debugging.repo.local.JokeDatabase

class JokeApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Di.jokeRepository = JokeRepository(
            remoteSource = RemoteSource(),
            localSource = LocalSource(JokeDatabase.getDatabase(context = this)),
        )
    }
}
package com.vk.edu.debugging.repo.local

import com.vk.edu.debugging.repo.JokeEntity

class LocalSource(db: JokeDatabase) {

    private val dao = db.jokeDao()

    suspend fun loadJokes(): List<JokeEntity> {
        return dao.getAll()
    }

    suspend fun saveJoke(joke: JokeEntity) {
        dao.insert(joke)
    }
}
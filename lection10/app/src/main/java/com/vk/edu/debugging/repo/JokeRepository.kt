package com.vk.edu.debugging.repo

import com.vk.edu.debugging.repo.local.LocalSource
import com.vk.edu.debugging.repo.remote.RemoteSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.time.measureTime

class JokeRepository(
    private val remoteSource: RemoteSource,
    private val localSource: LocalSource,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) {
    suspend fun loadJoke(): JokeEntity {
        return withContext(dispatcher) {
            // получаем шутку из сети
            val jokeDto = when (val result = remoteSource.requestJoke()) {
                is RemoteSource.RequestResult.Error -> null
                is RemoteSource.RequestResult.Ok -> result.joke
            }
            // сохрянямем в базу данных - single source of truth
            jokeDto?.let {
                localSource.saveJoke(it.mapToEntity())
            }
            // возвращаем последнюю сохранённую
            localSource.loadJokes().last()
        }
    }
}
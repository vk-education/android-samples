package com.vk.edu.debugging

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlin.time.measureTime

class MainViewModel : ViewModel() {
    private val repository = Di.jokeRepository
    val state = MutableStateFlow<JokeState>(JokeState.Loading)

    init {
        loadJoke()
    }

    fun loadJoke() {
        viewModelScope.launch {

            val joke = repository.loadJoke()

            state.value = JokeState.Data(
                jokeText = joke.joke,
                jokeUrl = joke.url,
            )
        }
    }

}

sealed interface JokeState {
    data object Loading : JokeState
    data class Data(
        val jokeText: String,
        val jokeUrl: String,
    ) : JokeState
}
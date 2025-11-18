package com.vk.lection06_concurrency_network

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.vk.lection06_concurrency_network.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val retrofitController by lazy {
        RetrofitController(API)
    }
    private val okHttpController by lazy {
        OkHttpController(API)
    }

    private val exceptionHandler = CoroutineExceptionHandler { _, e ->
        binding.joke.post {
            setError(e.message ?: e.toString())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnOkhttp.setOnClickListener {
            CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
                val jokeResult = okHttpController.requestJoke()
                processJokeResult(jokeResult)
            }
        }

        binding.btnRetrofit.setOnClickListener {
            CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
                val jokeResult = retrofitController.requestJoke()
                processJokeResult(jokeResult)
            }
        }
    }

    private suspend fun processJokeResult(jokeResult: Result) {
        withContext(Dispatchers.Main) {
            when (jokeResult) {
                is Result.Ok -> {
                    setJoke(jokeResult.joke.joke)
                }

                is Result.Error -> {
                    setError(jokeResult.error)
                }
            }
        }
    }

    private fun setJoke(joke: String) {
        with (binding.joke) {
            setTextColor(Color.BLACK)
            text = joke
        }
    }

    private fun setError(error: String) {
        with (binding.joke) {
            setTextColor(Color.RED)
            text = error
        }
    }

    companion object {
        const val API = "https://api.chucknorris.io"
    }
}
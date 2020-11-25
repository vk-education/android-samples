package com.example.githubapplication

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import com.example.githubapplication.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.test.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        prefs = getSharedPreferences("test", Context.MODE_PRIVATE)


        binding.applyButton.setOnClickListener {
            loadRepoForUser(binding.textField.text.toString())
        }
    }

    private val githubApi = App.retrofit.create(GithubApi::class.java)

    fun loadRepoForUser(username: String) = lifecycleScope.launch {
        Timber.i("Start load")
        val lastAnswer = getLastAnswer()
        if (lastAnswer != null) {
            setTest(lastAnswer.name)
        }
        val repo = githubApi.getUserRepos(username, perPage = 1).first()
        Timber.i("Get text $repo")
        setTest(repo.name)
        saveLastAnswer(repo)
    }

    suspend fun saveLastAnswer(answer: GitHubRepo) = withContext(Dispatchers.IO) {
        App.db.githubDao().insert(answer)
    }

    suspend fun getLastAnswer() = withContext(Dispatchers.IO) {
        App.db.githubDao().getAll().firstOrNull()
    }

    suspend fun setTest(text: String) = withContext(Dispatchers.Main) {
        Timber.i("Set text $text")
        binding.textOutput.text = text
    }
}
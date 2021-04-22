package com.lionzxy.testapplication

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.lionzxy.testapplication.databinding.ActivityMainBinding
import com.lionzxy.testapplication.requests.GetGithubRepoRequest
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            val repo = getRepos()
            setText(repo)
        }
    }

    suspend fun getRepos(): List<GithubRepo> = withContext(Dispatchers.IO) {
        GetGithubRepoRequest.getRepos()
    }

    suspend fun setText(repos: List<GithubRepo>) = withContext(Dispatchers.Main) {
        Log.i("MainActivity", "My repos count: ${repos.size}")
    }
}
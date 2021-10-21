package com.lionzxy.githubapplication.coroutines

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.lionzxy.githubapplication.databinding.ActivityMainBinding
import com.lionzxy.githubapplication.model.GithubRepo
import com.lionzxy.githubapplication.model.GithubUser
import com.lionzxy.githubapplication.view.GithubRecyclerAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivityCoroutines : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val adapter = GithubRecyclerAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        lifecycleScope.launch {
            val repositories = async { GithubDataStoreCoroutines.getRepositories("LionZXY") }
            val user = async { GithubDataStoreCoroutines.getUser("LionZXY") }
            showRepos(repositories.await(), user.await())
        }
    }

    private suspend fun showRepos(
        repos: List<GithubRepo>,
        user: GithubUser
    ) = withContext(Dispatchers.Main) {
        adapter.onNewItem(repos)
        Toast.makeText(this@MainActivityCoroutines, "${repos.size}", Toast.LENGTH_SHORT).show()
    }
}

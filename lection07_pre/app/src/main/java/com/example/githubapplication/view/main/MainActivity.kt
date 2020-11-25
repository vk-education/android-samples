package com.example.githubapplication.view.main

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.githubapplication.App
import com.example.githubapplication.AppDatabase
import com.example.githubapplication.GitHubRepo
import com.example.githubapplication.GithubApi
import com.example.githubapplication.databinding.ActivityMainBinding
import com.example.githubapplication.presenter.main.MainPresenter
import com.example.githubapplication.repository.db.DBRepository
import com.example.githubapplication.repository.network.NetworkRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class MainActivity : AppCompatActivity(), IMainView {
    private lateinit var binding: ActivityMainBinding
    private lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val githubApi = App.retrofit.create(
            GithubApi::class.java
        )
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database-name.db"
        ).build()
        val dbRepository = DBRepository(db)

        presenter = MainPresenter(
            lifecycleOwner = this,
            view = this,
            dbRepository = dbRepository,
            networkRepository = NetworkRepository(githubApi, dbRepository)
        )

        binding.applyButton.setOnClickListener {
            presenter.loadRepoForUser(binding.textField.text.toString())
        }
    }

    override fun setText(text: String) {
        binding.textOutput.text = text
    }
}
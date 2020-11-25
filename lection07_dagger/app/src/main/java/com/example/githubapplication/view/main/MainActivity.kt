package com.example.githubapplication.view.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.room.Room
import com.example.githubapplication.App
import com.example.githubapplication.model.AppDatabase
import com.example.githubapplication.model.GithubApi
import com.example.githubapplication.databinding.ActivityMainBinding
import com.example.githubapplication.presenter.main.MainPresenter
import com.example.githubapplication.repository.db.DBRepository
import com.example.githubapplication.repository.network.NetworkRepository
import retrofit2.Retrofit
import javax.inject.Inject

class MainActivity : AppCompatActivity(), IMainView {
    private lateinit var binding: ActivityMainBinding
    private lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = MainPresenter(
            lifecycleOwner = this,
            view = this
        )

        binding.applyButton.setOnClickListener {
            presenter.loadRepoForUser(binding.textField.text.toString())
        }
    }

    override fun setText(text: String) {
        binding.textOutput.text = text
    }
}
package com.lionzxy.githubapplication.rxjava

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.lionzxy.githubapplication.databinding.ActivityMainBinding
import com.lionzxy.githubapplication.model.GithubRepo
import com.lionzxy.githubapplication.model.GithubUser
import com.lionzxy.githubapplication.view.GithubRecyclerAdapter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

class MainActivityRxJava : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val adapter = GithubRecyclerAdapter()
    private val disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        val repoRequestDisposable = Observable.combineLatest(
            GithubDataStoreRxJava.getRepositories().toObservable(),
            GithubDataStoreRxJava.getUser().toObservable(),
            { repos, user -> repos to user }
        ).observeOn(AndroidSchedulers.mainThread())
            .subscribe({ (repos, user) ->
                showRepos(repos, user)
            }, {
                it.printStackTrace()
                Toast.makeText(this, "Errror!", Toast.LENGTH_SHORT).show()
            })

        disposables.add(repoRequestDisposable)
    }

    private fun showRepos(repos: List<GithubRepo>, user: GithubUser) {
        adapter.onNewItem(repos)
        Toast.makeText(this, "${repos.size}", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.dispose()
    }
}

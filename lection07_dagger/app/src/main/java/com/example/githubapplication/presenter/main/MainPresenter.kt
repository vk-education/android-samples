package com.example.githubapplication.presenter.main

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.example.githubapplication.App
import com.example.githubapplication.repository.db.DBRepository
import com.example.githubapplication.repository.network.NetworkRepository
import com.example.githubapplication.view.main.IMainView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class MainPresenter(
    private val lifecycleOwner: LifecycleOwner,
    private val view: IMainView
) {
    @Inject
    lateinit var networkRepository: NetworkRepository
    @Inject
    lateinit var dbRepository: DBRepository

    init {
        App.appComponent.inject(this)
    }

    fun loadRepoForUser(username: String) = lifecycleOwner.lifecycleScope.launch {
        Timber.i("Start load")
        val lastAnswer = dbRepository.getLastAnswer()
        if (lastAnswer != null) {
            setTest(lastAnswer.name)
        }
        val repo = networkRepository.getRepoForUser(username)
        Timber.i("Get text $repo")
        setTest(repo.name)
    }

    suspend fun setTest(text: String) = withContext(Dispatchers.Main) {
        Timber.i("Set text $text")
        view.setText(text)
    }
}
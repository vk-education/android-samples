package ru.hse.lection05.mvvm.jetpack.modules

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.hse.lection05.mvvm.jetpack.presentationlayer.models.GiphyViewModel

val app = module {
    viewModel { GiphyViewModel() }
}
package ru.hse.lection05.mvvm.simple.presentationlayer

import ru.hse.lection05.mvvm.simple.presentationlayer.models.GiphyViewModel

class ViewModelFactory {
    companion object {
        fun <VIEWMODEL> create(clazz: Class<VIEWMODEL>): VIEWMODEL? {
            return when(clazz) {
                GiphyViewModel::class.java -> GiphyViewModel() as VIEWMODEL
                else -> null
            }
        }
    }
}
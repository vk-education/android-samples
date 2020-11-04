package ru.hse.lection05.mvvm.jetpack.presentationlayer

import android.os.Bundle
import androidx.lifecycle.Observer
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.hse.lection05.base.presentationlayer.BaseActivity
import ru.hse.lection05.mvvm.jetpack.presentationlayer.models.*

class GiphyActivity : BaseActivity() {
    protected val viewModel by viewModel<GiphyViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.data().observe(this, Observer(::update))
    }

    override fun queryChanged(query: String) = viewModel.updateQuery(query)
    override fun refresh() = viewModel.refresh()


    protected fun update(state: State) {
        when(state) {
            is ItemListSuccess -> itemAdapter.submitList(state.result)
            is Fail -> showError(state.error?.message)

            else -> {
                // nothing to do
            }
        }

        refresher.isRefreshing = (state is Pending)
    }
}
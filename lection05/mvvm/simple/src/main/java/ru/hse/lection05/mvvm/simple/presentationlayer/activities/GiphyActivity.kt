package ru.hse.lection05.mvvm.simple.presentationlayer.activities

import ru.hse.lection05.base.presentationlayer.BaseActivity
import ru.hse.lection05.mvvm.simple.presentationlayer.ViewModelFactory
import ru.hse.lection05.mvvm.simple.presentationlayer.models.*

class GiphyActivity : BaseActivity(), GiphyViewModel.IView {
    protected val viewModel by lazy { ViewModelFactory.create(GiphyViewModel::class.java)!! }


    override fun onStart() {
        super.onStart()

        viewModel.subscribe(this)
    }

    override fun onStop() {
        viewModel.unsubscribe(this)

        super.onStop()
    }

    override fun queryChanged(query: String) = viewModel.updateQuery(query)
    override fun refresh() = viewModel.refresh()

    override fun update(state: State) {
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
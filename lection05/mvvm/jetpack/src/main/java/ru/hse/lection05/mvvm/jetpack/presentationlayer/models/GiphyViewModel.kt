package ru.hse.lection05.mvvm.jetpack.presentationlayer.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.koin.core.KoinComponent
import org.koin.core.inject
import ru.hse.lection05.api.giphy.businesslayer.GiphyProvider
import ru.hse.lection05.api.giphy.objects.ListResult

class GiphyViewModel: ViewModel(), KoinComponent {
    companion object {
        const val DATA_SIZE = 10

        const val EMPTY = ""
    }


    protected val provider by inject<GiphyProvider>()

    protected var query = EMPTY
    protected var interactor: AbstractInteractor = EmptyInteractor()

    protected val liveState = object: MutableLiveData<State>() {
        override fun onActive() {
            super.onActive()

            updateInteractor()
            executeInteractor(false)
        }
    }


    fun data(): LiveData<State> = liveState

    fun refresh() = executeInteractor(true)

    fun updateQuery(newQuery: String?) {
        val fixedQuery = newQuery?: EMPTY
        if (query == fixedQuery) {
            return
        }

        query = fixedQuery

        updateInteractor()
        executeInteractor(true)
    }


    protected fun updateInteractor() {
        val tag: String = query
        if (tag == interactor.tag) {
            return
        }

        interactor = when(tag.isEmpty()) {
            true -> TrendInteractor()
            false -> SearchInteractor(tag)
        }
    }

    protected fun updateState(interactor: AbstractInteractor) {
        if (this.interactor != interactor) {
            return
        }

        liveState.postValue(interactor.state)
    }

    protected fun executeInteractor(reset: Boolean) = interactor.handle(reset)


    abstract inner class AbstractInteractor(val tag: String?) {
        var state: State = None()
            protected set


        open fun handle(reset: Boolean) {
            // nothing to do
        }
    }

    inner class EmptyInteractor: AbstractInteractor(null)


    abstract inner class RequestInteractor(tag: String): AbstractInteractor(tag) {
        override fun handle(reset: Boolean) {
            if (state is Pending || (state.isFinal && !reset)) {
                return
            }


            state = Pending()
            updateState(this)

            request { result, error ->
                state = when (result) {
                    null -> Fail(error)
                    else -> ItemListSuccess(result.data)
                }

                updateState(this)
            }
        }


        protected abstract fun request(callback: (result: ListResult?, error: Throwable?) -> Unit)
    }

    inner class TrendInteractor: RequestInteractor(EMPTY) {
        override fun request(callback: (result: ListResult?, error: Throwable?) -> Unit) = provider.trending(DATA_SIZE, callback)
    }

    inner class SearchInteractor(val query: String): RequestInteractor(query) {
        override fun request(callback: (result: ListResult?, error: Throwable?) -> Unit) = provider.search(query, DATA_SIZE, callback)
    }
}
package ru.hse.lection05.mvvm.simple.presentationlayer.models

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.hse.lection05.api.giphy.businesslayer.GiphyProvider
import ru.hse.lection05.api.giphy.objects.ListResult
import ru.hse.lection05.mvvm.simple.businesslayer.ProviderFactory
import java.util.*

class GiphyViewModel() {
    companion object {
        const val DATA_SIZE = 10

        const val EMPTY = ""
    }


    interface IView {
        fun update(state: State)
    }


    protected val views = Collections.newSetFromMap(WeakHashMap<IView, Boolean>())

    protected val provider by lazy { ProviderFactory.get(GiphyProvider::class.java)!! }
    protected val mainScope = CoroutineScope(Dispatchers.Main)

    protected var trendState: State = None()
    protected var query = EMPTY


    fun subscribe(view: IView) {
        if (views.add(view)) {
            view.update(state())
        }
    }

    fun unsubscribe(view: IView) {
        views.remove(view)
    }

    fun refresh() {
        if (trendState is Pending) {
            return
        }

        trendState = None()
        state()
        trendsUpdated()
    }

    fun updateQuery(newQuery: String?) {
        val fixedQuery = newQuery?: EMPTY
        if (query == fixedQuery) {
            return
        }

        query = fixedQuery

        trendState = None()
        state()
    }


    protected fun trendsUpdated() {
        views.forEach {
            it.update(trendState)
        }
    }

    protected fun state(): State {
        if (trendState is None || trendState is Fail) {
            val state = Pending()
            trendState = state

            val callback: (result: ListResult?, error: Throwable?) -> Unit = { result, error ->
                val tmp = trendState
                if (tmp is Pending && state.tag == tmp.tag) {
                    trendState = when {
                        result == null -> Fail(error)
                        else -> ItemListSuccess(result.data)
                    }

                    mainScope.launch {
                        trendsUpdated()
                    }
                }
            }

            when (query.isEmpty()) {
                true -> provider.trending(DATA_SIZE, callback)
                false -> provider.search(query, DATA_SIZE, callback)
            }
        }

        return trendState
    }
}
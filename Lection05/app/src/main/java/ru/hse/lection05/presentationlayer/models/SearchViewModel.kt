package ru.hse.lection05.presentationlayer.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.hse.lection05.businesslayer.repositories.IPlaceRepository
import ru.hse.lection05.presentationlayer.Fail
import ru.hse.lection05.presentationlayer.Pending
import ru.hse.lection05.presentationlayer.States
import ru.hse.lection05.presentationlayer.Success
import ru.hse.lection05.objects.Place

@KoinApiExtension
class SearchViewModel: ViewModel(), KoinComponent {
    protected val provider by inject<IPlaceRepository>()  // вставка при помощи KOIN модуля
    protected val searchResult = MutableLiveData<States<List<Place>>>().apply {
        postValue(Success(emptyList()))
    }

    protected var currentQuery = ""


    fun searchResult(): LiveData<States<List<Place>>> = searchResult

    fun updateQuery(query: String) {
        if (currentQuery == query) {
            return
        }
        currentQuery = query

        execute()
    }

    fun retry() {
        execute()
    }

    protected fun execute() {
        searchResult.postValue(Pending())

        when (currentQuery.isEmpty()) {
            true -> searchResult.postValue(Success(emptyList()))

            false -> {
                provider.find(currentQuery) { result, error ->
                    val state = when {
                        result == null -> Fail(error)
                        else -> Success(result)
                    }

                    searchResult.postValue(state)
                }
            }
        }
    }
}
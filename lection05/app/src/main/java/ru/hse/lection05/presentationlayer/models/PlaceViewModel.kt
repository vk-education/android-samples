package ru.hse.lection05.presentationlayer.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.hse.lection05.businesslayer.repositories.IPlaceRepository
import ru.hse.lection05.businesslayer.repositories.RepositoryFactory
import ru.hse.lection05.presentationlayer.Fail
import ru.hse.lection05.presentationlayer.Pending
import ru.hse.lection05.presentationlayer.States
import ru.hse.lection05.presentationlayer.Success
import ru.hse.lection05.objects.Place

class PlaceViewModel: ViewModel() {
    protected val repository by lazy { RepositoryFactory.acqure(IPlaceRepository::class.java) }


    fun places(): LiveData<States<List<Place>>> {
        val liveData = MutableLiveData<States<List<Place>>>().apply {
            postValue(Pending())
        }

        repository.loadAll { result, error ->
            val state = when {
                result == null -> Fail(error)
                else -> Success(result)
            }

            liveData.postValue(state)
        }

        return liveData
    }

    fun save(item: Place, callback: (result: Boolean, error: Throwable?) -> Unit) {
        repository.save(item) { result, error ->
            viewModelScope.launch { callback(result, error) }
        }
    }
}
package company.vk.education.lection07.presentationlayer.models

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.core.os.postDelayed
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import company.vk.education.lection07.businesslayer.providers.IPlaceProvider
import company.vk.education.lection07.businesslayer.providers.Result
import company.vk.education.lection07.objects.AbstractPlace
import company.vk.education.lection07.presentationlayer.State
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class PlaceViewModel: ViewModel(), KoinComponent {
    protected val handler = Handler(Looper.getMainLooper())
    protected val provider by inject<IPlaceProvider>()
    protected val state = MutableLiveData<State<List<AbstractPlace>>>()


    var query = ""
        protected set

    fun state(): LiveData<State<List<AbstractPlace>>> = state

    fun find(newQuery: String) {
        if (newQuery == query) {
            return
        }
        query = newQuery

        postponedQuery(newQuery)
    }

    protected fun postponedQuery(query: String) {
        handler.removeCallbacksAndMessages(null)
        handler.postDelayed(600) {
            makeRequest(query)
        }
    }

    protected fun makeRequest(query: String) {
        state.postValue(State.Pending())

        provider.find(query) {
            val newState = when(it) {
                is Result.Success -> State.Success(it.data)
                is Result.Fail -> State.Fail(it.error)
            }

            state.postValue(newState)
        }
    }
}
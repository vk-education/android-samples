package ru.example.myapplication.presentationlayer

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import ru.example.myapplication.ServiceLocator.inject
import ru.example.myapplication.businesslayer.GifProvider
import ru.example.myapplication.objects.Item

class MainViewModel: ViewModel() {
    protected val provider by inject<GifProvider>()

    protected val state = MutableStateFlow<States<List<Item>>>(None())


    fun state(): Flow<States<List<Item>>> = state


    fun load() {
        state.value = Pending()

        provider.trending { result, error ->
            val newState = when {
                result == null -> Fail(error)
                else -> Success(result)
            }

            state.value = newState
        }
    }

    fun retry() {

    }
}
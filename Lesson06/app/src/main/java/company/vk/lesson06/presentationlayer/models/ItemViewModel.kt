package company.vk.lesson06.presentationlayer.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import company.vk.lesson06.ServiceLocator
import company.vk.lesson06.datalayer.ICharacterAccessor
import company.vk.lesson06.objects.Item
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ItemViewModel: ViewModel() {
    protected val accessor by lazy { ServiceLocator.inject<ICharacterAccessor>() }

    protected val state = MutableStateFlow<State<List<Item>>>(Pending())

    fun state(): Flow<State<List<Item>>> = state

    fun load() {
        state.value = Pending()
        viewModelScope.launch {
            val newState = try {
                val result = accessor.list(0, 20).results!!
                Success(result)
            } catch (error: Throwable) {
                error.printStackTrace()
                Fail(error)
            }

            state.value = newState
        }
    }
}
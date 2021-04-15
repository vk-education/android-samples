package ru.hse.lection05.businesslayer.repositories

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.hse.lection05.objects.Place

class OnlyErrorPlaceRepository(): IPlaceRepository {
    protected val scope = CoroutineScope(Dispatchers.IO)


    override fun save(place: Place, callback: (result: Boolean, error: Throwable?) -> Unit) {
        scope.launch {
            callback(false, IllegalStateException("invoked IPlaceRepository.save"))
        }
    }

    override fun loadAll(callback: (result: List<Place>?, error: Throwable?) -> Unit) {
        scope.launch {
            callback(null, IllegalStateException("invoked IPlaceRepository.loadAll"))
        }
    }

    override fun find(query: String, callback: (result: List<Place>?, error: Throwable?) -> Unit) {
        scope.launch {
            callback(null, IllegalStateException("invoked IPlaceRepository.find"))
        }
    }
}
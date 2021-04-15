package ru.hse.lection05.businesslayer.repositories

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.hse.lection05.datalayer.accessors.IApiAccessor
import ru.hse.lection05.datalayer.accessors.IOfflineAccessor
import ru.hse.lection05.datalayer.accessors.MemoryCacheAccessor
import ru.hse.lection05.objects.Place

class PlaceRepository(
    val onlineAccessor: IApiAccessor
    , val offlineAccessor: IOfflineAccessor<Place>
): IPlaceRepository {
    protected val scope = CoroutineScope(Dispatchers.IO)

    protected val cacheAccessor = MemoryCacheAccessor()


    override fun save(place: Place, callback: (result: Boolean, error: Throwable?) -> Unit) {
        scope.launch {
            try {
                callback(offlineAccessor.save(place), null)
            } catch (error: Throwable) {
                callback(false, error)
            }
        }
    }

    override fun loadAll(callback: (result: List<Place>?, error: Throwable?) -> Unit) {
        scope.launch {
            try {
                val cached = offlineAccessor.all()?: emptyList()
                val list = cached.toMutableList().apply {
                    sortBy { it.name }
                }
                callback(list, null)
            } catch (error: Throwable) {
                callback(null, error)
            }
        }
    }

    override fun find(query: String, callback: (result: List<Place>?, error: Throwable?) -> Unit) {
        scope.launch {
            try {
                val key = "find?query=$query"
                val cachedList = cacheAccessor.get<Place>(key)

                when (cachedList == null) {
                    true -> {
                        val result = onlineAccessor.find(query).list
                        cacheAccessor.put(key, result)
                        callback(result, null)
                    }
                    false -> callback(cachedList, null)
                }
            } catch (error: Throwable) {
                callback(null, error)
            }
        }
    }
}
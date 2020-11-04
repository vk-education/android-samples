package ru.hse.lection05.api.giphy.businesslayer

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.hse.lection05.api.giphy.datalayer.IDataAccessor
import ru.hse.lection05.api.giphy.datalayer.offline.IOfflineDataAccessor
import ru.hse.lection05.api.giphy.objects.ListResult

class GiphyProvider(val api: IDataAccessor) {
    companion object {
        const val TAG = "GiphyProvider"

        const val KEY_SEARCH_PATTERN = "search(%s,%s)"
        const val KEY_TRENDING_PATTERN = "trending(%s)"
    }


    protected val scope = CoroutineScope(Dispatchers.IO)

    var fastCache: IOfflineDataAccessor? = null
    var slowCache: IOfflineDataAccessor? = null


    fun search(query: String, limit: Int? = null, callback: (result: ListResult?, error: Throwable?) -> Unit) {
        val key = String.format(KEY_SEARCH_PATTERN, query, limit)
        execute(key, callback) { api.search(limit, query) }
    }

    fun trending(limit: Int? = null, callback: (result: ListResult?, error: Throwable?) -> Unit) {
        val key = String.format(KEY_TRENDING_PATTERN, limit)
        execute(key, callback) { api.trending(limit) }
    }

    protected fun execute(key: String, callback: (result: ListResult?, error: Throwable?) -> Unit, request: suspend () -> ListResult) {
        val result = findAndDuplicate(key, fastCache)

        if (result == null) {
            scope.launch {
                try {
                    var asyncResult = findAndDuplicate(key, slowCache, fastCache)

                    if (asyncResult == null) {
                        asyncResult = request()
                        Log.d(TAG, "found in API: ${asyncResult.data.size}")

                        updateCache(key, asyncResult, slowCache, fastCache)
                    }

                    callback(asyncResult, null)
                } catch (error: Throwable) {
                    error.printStackTrace()

                    callback(null, error)
                }
            }
        } else {
            callback(result, null)
        }
    }

    fun updateCache(key: String, data: ListResult?, vararg caches: IOfflineDataAccessor?) {
        caches.forEach {
            it?.putList(key, data)
        }
    }

    fun findAndDuplicate(key: String, main: IOfflineDataAccessor?, duplicate: IOfflineDataAccessor? = null): ListResult? {
        if (main == null) {
            return null
        }

        val result = main.getList(key)
        if (result != null) {
            Log.d(TAG, "found in ${main.javaClass.simpleName}: ${result.data.size}")
            updateCache(key, result, duplicate)
        }

        return result
    }
}
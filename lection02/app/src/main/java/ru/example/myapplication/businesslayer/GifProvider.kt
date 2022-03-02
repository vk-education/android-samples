package ru.example.myapplication.businesslayer

import kotlinx.coroutines.*
import ru.example.myapplication.ServiceLocator.inject
import ru.example.myapplication.datalayer.IGiphyApi
import ru.example.myapplication.objects.Item

class GifProvider {
    protected val api by inject<IGiphyApi>()

    protected val scope = CoroutineScope(Dispatchers.IO)


    fun trending(callback: (result: List<Item>?, error: Throwable?) -> Unit) {
        scope.launch {
            try {
                val result = api.trending().data
                withContext(Dispatchers.Main) {
                    callback(result, null)
                }
            } catch (error: Throwable) {
                withContext(Dispatchers.Main) {
                    callback(null, error)
                }
            }
        }
    }
}
package company.vk.education.lection07.businesslayer.providers

import company.vk.education.lection07.datalayer.accessors.IOpenWeatherAccessor
import company.vk.education.lection07.objects.AbstractPlace
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class OpenWeatherPlaceProvider(val onlineAccessor: IOpenWeatherAccessor): AbstractCoroutineProvider(), IPlaceProvider {
    override fun find(query: String, callback: (result: Result<List<AbstractPlace>>) -> Unit) {
        scope.launch {
            if (query.length < 2) {
                withContext(Dispatchers.Main) {
                    callback(Result.Success(emptyList()))
                }
                return@launch
            }

            val result = try {
                val apiResult = onlineAccessor.find(query).list as List<AbstractPlace>
                Result.Success(apiResult)
            } catch (error: Throwable) {
                Result.Fail(error)
            }

            withContext(Dispatchers.Main) {
                callback(result)
            }
        }
    }
}
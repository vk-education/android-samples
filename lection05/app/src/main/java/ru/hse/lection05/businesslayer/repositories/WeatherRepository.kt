package ru.hse.lection05.businesslayer.repositories

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.hse.lection05.datalayer.accessors.IApiAccessor
import ru.hse.lection05.objects.WeatherData

class WeatherRepository(
    val onlineAccessor: IApiAccessor
): IWeatherRepository {
    protected val scope = CoroutineScope(Dispatchers.IO)


    override fun weather(cityId: Long, callback: (result: WeatherData?, error: Throwable?) -> Unit) {
        scope.launch {
            try {
                callback(onlineAccessor.weather(cityId), null)
            } catch (error: Throwable) {
                callback(null, error)
            }
        }
    }
}
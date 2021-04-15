package ru.hse.lection05.presentationlayer.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.hse.lection05.businesslayer.repositories.IWeatherRepository
import ru.hse.lection05.presentationlayer.Fail
import ru.hse.lection05.presentationlayer.Pending
import ru.hse.lection05.presentationlayer.States
import ru.hse.lection05.presentationlayer.Success
import ru.hse.lection05.objects.Place
import ru.hse.lection05.objects.WeatherData

@KoinApiExtension
class WeatherViewModel: ViewModel(), KoinComponent {
    protected val repository by inject<IWeatherRepository>()
    protected val weatherData = MutableLiveData<States<WeatherData>>()


    fun weatherData(): LiveData<States<WeatherData>> = weatherData

    fun load(place: Place) {
        weatherData.postValue(Pending())
        repository.weather(place.id) { result, error ->
            val state = when {
                result == null -> Fail(error)
                else -> Success(result)
            }

            weatherData.postValue(state)
        }
    }
}
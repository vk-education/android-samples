package ru.hse.lection05.businesslayer.repositories

import ru.hse.lection05.objects.WeatherData

interface IWeatherRepository {
    fun weather(cityId: Long, callback: (result: WeatherData?, error: Throwable?) -> Unit)
}
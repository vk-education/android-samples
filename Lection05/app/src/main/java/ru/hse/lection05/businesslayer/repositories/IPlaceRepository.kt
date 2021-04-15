package ru.hse.lection05.businesslayer.repositories

import ru.hse.lection05.objects.Place

interface IPlaceRepository {
    fun save(place: Place, callback: (result: Boolean, error: Throwable?) -> Unit)
    fun loadAll(callback: (result: List<Place>?, error: Throwable?) -> Unit)

    fun find(query: String, callback: (result: List<Place>?, error: Throwable?) -> Unit)
}
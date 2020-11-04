package ru.hse.lection05.api.giphy.datalayer.offline

import ru.hse.lection05.api.giphy.objects.ListResult

interface IOfflineDataAccessor {
    fun putList(key: String, list: ListResult?)
    fun getList(key: String): ListResult?
}
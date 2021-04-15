package ru.hse.lection05.datalayer.accessors

import java.util.concurrent.TimeUnit

interface ICacheAccessor {
    fun <TYPE> get(key: String, maxLiveTimeInMillis: Long = TIME_5_MINUTES): List<TYPE>?
    fun <TYPE> put(key: String, list: List<TYPE>?)


    companion object {
        val TIME_5_MINUTES = TimeUnit.MINUTES.toMillis(5)
    }
}
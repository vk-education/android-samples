package ru.hse.lection05.datalayer.accessors

import android.os.SystemClock

class MemoryCacheAccessor(): ICacheAccessor {
    val map = mutableMapOf<String, Pair<Long, List<*>>>()


    override fun <TYPE> get(key: String, maxLiveTimeInMillis: Long): List<TYPE>? {
        val pair = map[key]
        if (pair == null) {
            return null
        }

        val deltaTime = SystemClock.elapsedRealtime() - pair.first
        if (deltaTime > maxLiveTimeInMillis) {
            map.remove(key)
            return null
        }

        return pair.second as List<TYPE>
    }

    override fun <TYPE> put(key: String, list: List<TYPE>?) {
        when {
            list == null -> map.remove(key)
            else -> map[key] = Pair(SystemClock.elapsedRealtime(), list)
        }
    }
}
package ru.hse.lection05.base.datalayer.offline

import android.os.SystemClock
import ru.hse.lection05.api.giphy.datalayer.offline.IOfflineDataAccessor
import ru.hse.lection05.api.giphy.objects.ListResult
import java.util.concurrent.TimeUnit

class MemoryDataAccessor(protected val liveTime: Long = LIVE_TIME): IOfflineDataAccessor {
    companion object {
        protected val LIVE_TIME = TimeUnit.SECONDS.toMillis(10)
    }

    protected val entries = mutableMapOf<String, Entry<ListResult>>()


    override fun putList(key: String, list: ListResult?) {
        validateAll()

        when {
            list == null -> entries.remove(key)
            else -> {
                entries[key] = Entry(list)
            }
        }
    }

    override fun getList(key: String): ListResult? {
        val entry = entries[key]
        if (check(entry)) {
            return entry?.data
        }

        entries.remove(key)

        return null
    }


    protected fun validateAll() {
        entries.entries.removeAll {
            it.value.time + liveTime < SystemClock.elapsedRealtime()
        }
    }

    protected fun check(entry: Entry<*>?): Boolean {
        if (entry != null && entry.time + liveTime < SystemClock.elapsedRealtime()) {
            return false
        }

        return true
    }


    class Entry<TYPE>(val data: TYPE) {
        val time = SystemClock.elapsedRealtime()
    }
}
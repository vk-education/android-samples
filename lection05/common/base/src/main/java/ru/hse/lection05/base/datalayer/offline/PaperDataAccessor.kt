package ru.hse.lection05.base.datalayer.offline

import android.os.SystemClock
import io.paperdb.Book
import io.paperdb.Paper
import ru.hse.lection05.api.giphy.datalayer.offline.IOfflineDataAccessor
import ru.hse.lection05.api.giphy.objects.ListResult
import java.util.concurrent.TimeUnit

class PaperDataAccessor(tag: String, val liveTime: Long = LIVE_TIME): IOfflineDataAccessor {
    companion object {
        protected val LIVE_TIME = TimeUnit.MINUTES.toMillis(5)
    }


    val book: Book


    init {
        book = Paper.book(tag)
    }



    override fun putList(key: String, list: ListResult?) {
        when {
            list == null -> book.delete(key)
            else -> book.write(key, Entry(list))
        }
    }

    override fun getList(key: String): ListResult? {
        if (!book.contains(key)) {
            return null
        }

        try {
            val result = book.read<Entry>(key)
            if (check(result)) {
                return result?.data
            }
        } catch (error: Throwable) {
            error.printStackTrace()
        }

        book.delete(key)

        return null
    }


    protected fun check(entry: Entry?): Boolean {
        if (entry != null && entry.time + liveTime < SystemClock.elapsedRealtime()) {
            return false
        }

        return true
    }


    class Entry(val data: ListResult) {
        val time = SystemClock.elapsedRealtime()
    }
}
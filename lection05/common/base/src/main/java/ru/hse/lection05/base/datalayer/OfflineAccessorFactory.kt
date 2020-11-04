package ru.hse.lection05.base.datalayer

import android.content.Context
import io.paperdb.Paper
import ru.hse.lection05.api.giphy.datalayer.offline.IOfflineDataAccessor
import ru.hse.lection05.base.datalayer.offline.MemoryDataAccessor
import ru.hse.lection05.base.datalayer.offline.PaperDataAccessor

class OfflineAccessorFactory(context: Context) {
    init {
        Paper.init(context)
    }


    fun memoryAccessor(tag: String): IOfflineDataAccessor {
        return MemoryDataAccessor()
    }

    fun persistentAccessor(tag: String): IOfflineDataAccessor {
        return PaperDataAccessor(tag)
    }
}
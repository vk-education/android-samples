package ru.hse.lection05.datalayer.accessors

import io.paperdb.Paper
import ru.hse.lection05.objects.AbstractObject

class PaperOfflineAccessor<TYPE: AbstractObject>(val storageName: String): IOfflineAccessor<TYPE> {
    override fun all(): List<TYPE>? {
        return Paper.book().read(storageName, listOf())
    }

    override fun save(item: TYPE): Boolean {
        val list = all()?: emptyList()
        val set = list.toMutableSet()
        val result = set.add(item)


        if (result) {
            Paper.book().write(storageName, set.toList())
        }

        return result
    }

    override fun remove(item: TYPE): Boolean {
        val list = all()?: emptyList()
        val set = list.toMutableSet()
        val result = set.remove(item)


        if (result) {
            Paper.book().write(storageName, set.toList())
        }

        return result
    }
}
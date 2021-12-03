package ru.hse.lection05.datalayer.accessors

import ru.hse.lection05.objects.AbstractObject

class MemoryOfflineAccessor<TYPE: AbstractObject>: IOfflineAccessor<TYPE> {
    val keeper = mutableSetOf<TYPE>()

    override fun all() = keeper.toList()
    override fun save(item: TYPE) = keeper.add(item)
    override fun remove(item: TYPE) = keeper.remove(item)

}
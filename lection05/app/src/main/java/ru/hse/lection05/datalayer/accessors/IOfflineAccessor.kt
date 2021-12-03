package ru.hse.lection05.datalayer.accessors

interface IOfflineAccessor<TYPE> {
    fun all(): List<TYPE>?
    fun save(item: TYPE): Boolean
    fun remove(item: TYPE): Boolean
}
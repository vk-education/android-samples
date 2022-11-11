package com.vkeducation.lection07.db

import android.annotation.SuppressLint
import android.content.Context
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.concurrent.Executor
import java.util.concurrent.Executors

/**
 * Менеджер, управляющий базой данных. Занимается чтением/записью данных в выделенном потоке.
 * Все callback'и вызываются в потоке исполнения (не переводятся в UI-thread).
 */
internal class DbManagerRoom {
    private val executor: Executor = Executors.newSingleThreadExecutor()
    private lateinit var context: Context
    fun insert(text: String) {
        executor.execute {
            insertRoom(text)
        }
    }

    fun readAll(listener: ReadAllListener<String?>) {
        executor.execute {
            readAllRoom(listener)
        }
    }

    fun queryAll() : Flow<List<String>> {
        val dao: SimpleEntityDao = AppDatabase.getInstance(context).dao
        return dao.allEntitiesFlow.map { list -> list.map { it.text ?: "" } }
    }

    fun clean() {
        executor.execute {
            cleanRoom()
        }
    }

    private fun cleanRoom() {
        val dao: SimpleEntityDao = AppDatabase.getInstance(context).dao
        dao.delete(*dao.allEntities.toTypedArray())
    }

    private fun insertRoom(text: String) {
        val simpleEntity = SimpleEntity()
        simpleEntity.text = text
        AppDatabase.getInstance(context).dao.insertAll(simpleEntity)
    }

    private fun readAllRoom(listener: ReadAllListener<String?>) {
        val list: List<SimpleEntity> =
            AppDatabase.getInstance(context).dao.allEntities
        val strings = ArrayList<String?>()
        for (simpleEntity in list) {
            strings.add(simpleEntity.text)
        }
        listener.onReadAll(strings)
    }

    interface ReadAllListener<T> {
        fun onReadAll(allItems: Collection<T>)
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        private val INSTANCE = DbManagerRoom()
        fun getInstance(context: Context): DbManagerRoom {
            INSTANCE.context = context.applicationContext
            return INSTANCE
        }
    }
}
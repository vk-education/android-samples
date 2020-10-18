package ru.hse.lection04.businesslayer

import android.content.ContentValues
import android.content.Context
import android.database.ContentObserver
import android.os.Handler
import android.os.Looper
import ru.hse.lection04.datalayer.LogDataAccessor
import ru.hse.lection04.objects.LogEntry
import java.text.SimpleDateFormat
import java.util.*

/**
 * Класс для работы с логами. Обеспечивает так же подписку на изменение данных
 */
class LogProvider(protected val mContext: Context) : AbstractCallbackProvider<LogProvider.IListener?>() {
    /**
     * Подписчик, который будет уведомляться, когда прозишли изменения данных
     */
    interface IListener {
        fun logUpdated(provider: LogProvider?, entries: List<LogEntry>?)
    }

    /**
     * Внутренний класс для остлеживания обновления ContentProvider с логами
     */
    protected val mUpdateListener = UpdateListener()
    override fun setActivation(value: Boolean) {
        super.setActivation(value)
        if (value) {
            mContext.contentResolver.registerContentObserver(LogDataAccessor.Companion.CONTENT_URI, true, mUpdateListener)
        } else {
            mContext.contentResolver.unregisterContentObserver(mUpdateListener)
        }
    }

    /**
     * Добавить запись в лог
     * @param time время события
     * @param message текст события
     */
    fun write(time: Date?, message: String?) {
        val values = ContentValues()
        values.put(LogDataAccessor.Companion.COLUMN_DATE, DATE_FORMATTER.format(time))
        values.put(LogDataAccessor.Companion.COLUMN_TYPE, message)
        mContext.contentResolver.insert(LogDataAccessor.Companion.CONTENT_URI, values)
    }

    /**
     * Получить список логов
     * @return Список из объектов LogEntry
     */
    protected fun all(): List<LogEntry> {
        val cursor = mContext.contentResolver.query(LogDataAccessor.Companion.CONTENT_URI, null, null, null, null)
        val result: MutableList<LogEntry> = ArrayList()
        cursor!!.move(-1)
        while (cursor.moveToNext()) {
            val item = LogEntry()
            val dateIndex = cursor.getColumnIndex(LogDataAccessor.Companion.COLUMN_DATE)
            item.time = cursor.getString(dateIndex)
            val typeIndex = cursor.getColumnIndex(LogDataAccessor.Companion.COLUMN_TYPE)
            item.message = cursor.getString(typeIndex)
            result.add(item)
        }
        return result
    }

    protected fun notifyListeners() {
        HANDLER_MAIN.post(ListenerNotificator(this, listeners, all()))
    }

    /**
     * Подписчик на изменения ContentProvider
     */
    protected inner class UpdateListener : ContentObserver(null) {
        override fun onChange(selfChange: Boolean) {
            super.onChange(selfChange)
            notifyListeners()
        }
    }

    /**
     * Класс для обновления слушателей
     */
    protected class ListenerNotificator(protected val provider: LogProvider, protected val listeners: MutableSet<IListener?>, protected val entries: List<LogEntry>) : Runnable {
        override fun run() {
            listeners.forEach { listener ->
                listener?.logUpdated(provider, entries)
            }
        }
    }

    companion object {
        const val TAG = "LogProvider"
        protected val HANDLER_MAIN = Handler(Looper.getMainLooper())
        protected val DATE_FORMATTER = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.US)
    }

}
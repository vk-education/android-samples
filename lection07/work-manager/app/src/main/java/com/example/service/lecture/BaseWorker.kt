package com.example.service.lecture

import android.content.Context
import android.util.Log
import androidx.work.*
import kotlinx.coroutines.delay

abstract class BaseWorker(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {

    // if expedited
    override suspend fun getForegroundInfo(): ForegroundInfo {
        return ForegroundInfo(
            getNotificationId(),
            createNotification(applicationContext, getName(), "Im so important")
        )
    }

    override suspend fun doWork(): Result {
        val msg = inputData.getString(MSG_KEY).orEmpty()
        val isForeground = inputData.getBoolean(MAKE_FOREGROUND_KEY, false)
        val workTime = inputData.getLong(WORK_TIME_KEY, 10000L)

        if (isForeground) {
            setForegroundAsync(
                ForegroundInfo(
                    getNotificationId(),
                    createNotification(applicationContext, getName(), msg)
                )
            )
        }
        Log.d("Worker", "start worker ${getName()} with msg:${msg}")
        delay(workTime)
        Log.d("Worker", "end worker ${getName()} with msg:${msg}")
        return Result.success()
    }

    abstract fun getName(): String
    abstract fun getNotificationId(): Int

    companion object {
        private const val MSG_KEY = "MSG_KEY"
        private const val MAKE_FOREGROUND_KEY = "MAKE_FOREGROUND"
        private const val WORK_TIME_KEY = "WORK_TIME"

        fun getWorkData(msg: String, isForeground: Boolean = false, workTime: Long = 10000L): Data {
            return workDataOf(
                MSG_KEY to msg,
                MAKE_FOREGROUND_KEY to isForeground,
                WORK_TIME_KEY to workTime
            )
        }
    }
}
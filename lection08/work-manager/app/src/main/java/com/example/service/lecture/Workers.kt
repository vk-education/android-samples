package com.example.service.lecture

import android.content.Context
import androidx.work.WorkerParameters

class WorkerOne(appContext: Context, params: WorkerParameters) : BaseWorker(appContext, params) {
    override fun getName(): String = "WorkerOne"
    override fun getNotificationId(): Int = 1
}

class WorkerTwo(appContext: Context, params: WorkerParameters) : BaseWorker(appContext, params) {
    override fun getName(): String = "WorkerTwo"
    override fun getNotificationId(): Int = 2
}

class WorkerThree(appContext: Context, params: WorkerParameters) : BaseWorker(appContext, params) {
    override fun getName(): String = "WorkerThree"
    override fun getNotificationId(): Int = 3
}

class WorkerFour(appContext: Context, params: WorkerParameters) : BaseWorker(appContext, params) {
    override fun getName(): String = "WorkerFour"
    override fun getNotificationId(): Int = 4
}
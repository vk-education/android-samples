package com.mycompany.servicesexample

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class MyWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    private val tag = "MyWorker"

    override fun doWork(): Result {
        var i = 0;
        while (i < 5) {
            Log.d(tag, "New count = $i")
            Thread.sleep(1000)
            i++
        }
        return Result.success()
    }
}
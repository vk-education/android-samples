package com.example.lecture5.presentation

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.example.lecture5.data.provider.TaskJournal
import com.example.lecture5.data.provider.TaskStackProvider

class App : Application() {

    private val taskJournal = TaskJournal()
    val taskStackProvider = TaskStackProvider(taskJournal)

    override fun onCreate() {
        super.onCreate()
        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) =
                taskJournal.onCreate(activity)

            override fun onActivityResumed(activity: Activity) = taskJournal.onResume(activity)
            override fun onActivityDestroyed(activity: Activity) = taskJournal.onDestroy(activity)

            // not usage
            override fun onActivityStarted(activity: Activity) = Unit
            override fun onActivityPaused(activity: Activity) = Unit
            override fun onActivityStopped(activity: Activity) = Unit
            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) = Unit


        })
    }
}
package com.example.lecture5

import android.app.Activity
import android.app.Application
import android.os.Bundle

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) =
                activity.log("Created", savedInstanceState)

            override fun onActivityStarted(activity: Activity) = activity.log("Started")

            override fun onActivityResumed(activity: Activity) = activity.log("Resumed")

            override fun onActivityPaused(activity: Activity) = activity.log("Paused")

            override fun onActivityStopped(activity: Activity) = activity.log("Stopped")

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) =
                activity.log("SaveInstanceState", outState)

            override fun onActivityDestroyed(activity: Activity) = activity.log("Destroyed")
        })
    }
}
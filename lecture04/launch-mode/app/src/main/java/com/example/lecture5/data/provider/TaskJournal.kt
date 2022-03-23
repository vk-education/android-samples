package com.example.lecture5.data.provider

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.content.pm.PackageManager
import com.example.lecture5.ext.taskIdCompat
import com.example.lecture5.model.ActivityData
import com.example.lecture5.model.TaskData
import com.example.lecture5.presentation.activities.BaseActivity
import com.example.lecture5.util.queryLaunchedFlags


typealias OnJournalUpdate = (currentTaskId: Int, currentActivityId: Int, Map<Int, TaskData>) -> Unit

class TaskJournal {
    private var currentTaskId = -1
    private var currentActivityId = -1
    private val taskDataMap = linkedMapOf<Int, TaskData>()
    private val journalListeners = mutableListOf<OnJournalUpdate>()

    fun onCreate(activity: Activity) {
        val activityManager = activity.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val appTasks = activityManager.appTasks
        currentTaskId = activity.taskId
        currentActivityId = (activity as BaseActivity).uniqueId

        val taskInfo = appTasks.firstOrNull { it.taskInfo.taskIdCompat == currentTaskId }?.taskInfo

        if (taskInfo != null) {
            val taskData = taskDataMap.getOrPut(taskInfo.taskIdCompat) {
                val affinity = try {
                    activity.packageManager.getActivityInfo(
                        taskInfo.baseActivity!!,
                        PackageManager.GET_META_DATA
                    ).taskAffinity
                } catch (e: Exception) {
                    "Unknown"
                }
                TaskData(taskInfo.taskIdCompat, affinity)
            }

            val key = activity.uniqueId
            taskData.activitiesMap[key] = ActivityData(
                hash = key,
                name = activity.javaClass.simpleName,
                launchedFlags = queryLaunchedFlags(activity.intent).joinToString(", ")
            )
        }
        updateData()
    }

    fun onResume(activity: Activity) {
        currentTaskId = activity.taskId
        currentActivityId = (activity as BaseActivity).uniqueId
        updateData()
    }

    fun onDestroy(activity: Activity) {
        val activityManager = activity.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val appTasks = activityManager.appTasks

        val taskInfo =
            appTasks.firstOrNull { it.taskInfo.taskIdCompat == activity.taskId }?.taskInfo
        val activityKey = (activity as BaseActivity).uniqueId
        val taskData = if (taskInfo != null) {
            taskDataMap[taskInfo.taskIdCompat]
        } else {
            taskDataMap.values.firstOrNull {
                it.activitiesMap.containsKey(activityKey)
            }
        }
        taskData?.activitiesMap?.remove(activityKey)
        if (taskData?.activitiesMap?.isNullOrEmpty() == true) {
            taskDataMap.remove(taskData.taskId)
        }
        updateData()
    }

    fun onNewData(listener: OnJournalUpdate) {
        journalListeners += listener
    }

    private fun updateData() {
        journalListeners.forEach { it.invoke(currentTaskId, currentActivityId, taskDataMap) }
    }


}



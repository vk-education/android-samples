package com.example.lecture5.data.provider

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.lecture5.model.StackInfo
import com.example.lecture5.model.TaskData

class TaskStackProvider(
    taskJournal: TaskJournal
) {
    private val stackLiveData = MutableLiveData<List<StackInfo>>()
    val stackInfo: LiveData<List<StackInfo>> = stackLiveData

    init {
        taskJournal.onNewData { currentTaskId, currentActivityId, taskDataMap ->
            stackLiveData.value =
                convertTaskDataToStackInfo(currentTaskId, currentActivityId, taskDataMap)
        }
    }

    private fun convertTaskDataToStackInfo(
        currentTaskId: Int,
        currentActivityId: Int,
        taskDataMap: Map<Int, TaskData>
    ): List<StackInfo> {
        return taskDataMap.values.reversed()
            .fold(mutableListOf()) { addTask, taskData ->
                addTask += StackInfo.TaskInfo(
                    taskId = taskData.taskId,
                    isSelected = taskData.taskId == currentTaskId,
                    taskAffinity = taskData.taskAffinity
                )
                taskData.activitiesMap.values.reversed().fold(addTask) { addActivity, activity ->
                    addActivity += StackInfo.ActivityInfo(
                        hash = activity.hash,
                        name = activity.name,
                        isSelected = activity.hash == currentActivityId,
                        launchedFlags = activity.launchedFlags
                    )
                    addActivity
                }
                addTask
            }
    }

}


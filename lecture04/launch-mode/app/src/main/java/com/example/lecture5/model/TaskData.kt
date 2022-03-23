package com.example.lecture5.model

data class TaskData(
    val taskId: Int,
    val taskAffinity: String,
    val activitiesMap: LinkedHashMap<Int, ActivityData> = linkedMapOf()
)

data class ActivityData(
    val hash: Int,
    val name: String,
    val launchedFlags: String
)
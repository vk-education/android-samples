package com.example.lecture5.ext

import android.app.ActivityManager.RecentTaskInfo
import android.os.Build

val RecentTaskInfo.taskIdCompat: Int
    get() = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) this.id else this.taskId
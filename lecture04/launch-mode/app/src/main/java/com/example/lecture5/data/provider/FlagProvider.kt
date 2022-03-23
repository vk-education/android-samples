package com.example.lecture5.data.provider;

import android.content.Intent;
import com.example.lecture5.model.Flag


class FlagProvider {
    val flags = listOf(
        Flag("FLAG_ACTIVITY_NEW_TASK", Intent.FLAG_ACTIVITY_NEW_TASK),
        Flag("FLAG_ACTIVITY_CLEAR_TASK", Intent.FLAG_ACTIVITY_CLEAR_TASK),
        Flag("FLAG_ACTIVITY_CLEAR_TOP", Intent.FLAG_ACTIVITY_CLEAR_TOP),
        Flag("FLAG_ACTIVITY_NEW_DOCUMENT", Intent.FLAG_ACTIVITY_NEW_DOCUMENT),
        Flag("FLAG_ACTIVITY_MULTIPLE_TASK", Intent.FLAG_ACTIVITY_MULTIPLE_TASK),
        Flag("FLAG_ACTIVITY_NO_HISTORY", Intent.FLAG_ACTIVITY_NO_HISTORY),
        Flag("FLAG_ACTIVITY_REORDER_TO_FRONT", Intent.FLAG_ACTIVITY_REORDER_TO_FRONT),
        Flag("FLAG_ACTIVITY_RETAIN_IN_RECENTS", Intent.FLAG_ACTIVITY_RETAIN_IN_RECENTS),
        Flag("FLAG_ACTIVITY_SINGLE_TOP", Intent.FLAG_ACTIVITY_SINGLE_TOP),
        Flag("FLAG_ACTIVITY_RETAIN_IN_RECENTS", Intent.FLAG_ACTIVITY_RETAIN_IN_RECENTS),
        Flag("FLAG_ACTIVITY_LAUNCH_ADJACENT", Intent.FLAG_ACTIVITY_LAUNCH_ADJACENT),
    )

    private val _selectedFlags = mutableSetOf<Flag>()
    val selectedFlags: Set<Flag>
        get() = _selectedFlags.toSet()

    fun setFlag(flag: Flag) {
        _selectedFlags += flag
    }

    fun removeFlag(flag: Flag) {
        _selectedFlags -= flag
    }

    fun isInSelected(flag: Flag) = _selectedFlags.contains(flag)
}



package com.example.lecture5.presentation.adapters.stack

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.lecture5.R
import com.example.lecture5.adapters.inflate
import com.example.lecture5.model.StackInfo
import com.example.lecture5.model.StackInfo.ActivityInfo
import com.example.lecture5.model.StackInfo.TaskInfo

val diffUtil = object : DiffUtil.ItemCallback<StackInfo>() {
    override fun areItemsTheSame(
        oldItem: StackInfo,
        newItem: StackInfo
    ): Boolean {
        return (newItem is TaskInfo
                && oldItem is TaskInfo
                && oldItem.taskId == newItem.taskId) ||
                (newItem is ActivityInfo
                        && oldItem is ActivityInfo
                        && oldItem.hash == newItem.hash)
    }

    override fun areContentsTheSame(
        oldItem: StackInfo,
        newItem: StackInfo
    ): Boolean {
        return oldItem == newItem
    }

}


class StackAdapter : ListAdapter<StackInfo, StackViewHolder>(diffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StackViewHolder =
        when (viewType) {
            R.layout.view_holder_task_info -> TaskViewHolder(parent.inflate(viewType))
            else -> ActivityViewHolder(parent.inflate(viewType))
        }

    override fun onBindViewHolder(holder: StackViewHolder, position: Int) {
        when (holder) {
            is ActivityViewHolder -> holder.bind(getItem(position) as ActivityInfo)
            is TaskViewHolder -> holder.bind(getItem(position) as TaskInfo)
        }
    }

    override fun getItemViewType(position: Int): Int = when (getItem(position)) {
        is TaskInfo -> R.layout.view_holder_task_info
        is ActivityInfo -> R.layout.view_holder_activity_info
    }
}

sealed class StackViewHolder(view: View) : RecyclerView.ViewHolder(view)

class TaskViewHolder(view: View) : StackViewHolder(view) {
    private val textView = view as TextView

    @SuppressLint("SetTextI18n")
    fun bind(task: TaskInfo) {
        textView.text = "TaskId: ${task.taskId}\nTaskAffinity: ${task.taskAffinity}"
    }
}

class ActivityViewHolder(view: View) : StackViewHolder(view) {
    private val nameTextView = view.findViewById<TextView>(R.id.name_and_hash_text_view)
    private val flagsTextView = view.findViewById<TextView>(R.id.flags_text_view)

    @SuppressLint("SetTextI18n")
    fun bind(task: ActivityInfo) = with(task) {
        nameTextView.text = "${name}(#${hash.toString(16)})"
        flagsTextView.text = launchedFlags
        if (launchedFlags.isEmpty()) flagsTextView.visibility = View.GONE
        else flagsTextView.visibility = View.VISIBLE
        itemView.isSelected = isSelected
    }
}
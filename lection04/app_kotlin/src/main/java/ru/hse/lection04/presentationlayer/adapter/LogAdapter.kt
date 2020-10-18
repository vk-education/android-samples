package ru.hse.lection04.presentationlayer.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import ru.hse.lection04.R
import ru.hse.lection04.objects.LogEntry

class LogAdapter : ListAdapter<LogEntry?, LogViewHolder?>(Differ()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LogViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_log, parent, false)
        return LogViewHolder(view)
    }

    override fun onBindViewHolder(holder: LogViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    protected class Differ : DiffUtil.ItemCallback<LogEntry?>() {
        override fun areItemsTheSame(oldItem: LogEntry, newItem: LogEntry): Boolean {
            return oldItem.time == newItem.time
        }

        override fun areContentsTheSame(oldItem: LogEntry, newItem: LogEntry): Boolean {
            return oldItem.message == newItem.message
        }
    }
}
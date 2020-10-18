package ru.hse.lection04.presentationlayer.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.hse.lection04.R
import ru.hse.lection04.objects.LogEntry

class LogViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    companion object {
        protected const val MESSAGE_PATTERN = "[%s] - %s"
    }


    protected val text: TextView


    init {
        text = itemView.findViewById(R.id.text)
    }


    fun bind(item: LogEntry?) {
        text.text = String.format(MESSAGE_PATTERN, item?.time, item?.message)
    }
}
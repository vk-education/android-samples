package com.example.lecture5.presentation.adapters.activity

import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lecture5.R
import com.example.lecture5.adapters.inflate
import com.example.lecture5.model.ActivityStartInfo
import com.example.lecture5.presentation.activities.BaseActivity

class ActivityStartAdapter(
    private val activities: List<ActivityStartInfo>,
    private val onStart: (Class<out BaseActivity>) -> Unit
) : RecyclerView.Adapter<ActivityStartViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityStartViewHolder =
        ActivityStartViewHolder(
            parent.inflate(R.layout.view_holder_activity_start_info),
            onStart
        )

    override fun onBindViewHolder(holder: ActivityStartViewHolder, position: Int) {
        holder.bind(activities[position])
    }

    override fun getItemCount(): Int = activities.size
}


class ActivityStartViewHolder(
    view: View,
    private val onStart: (Class<out BaseActivity>) -> Unit
) : RecyclerView.ViewHolder(view) {

    private var currentInfo: ActivityStartInfo? = null

    private val descriptionTextView = view.findViewById<TextView>(R.id.description_text_view)
    private val aButton = view.findViewById<Button>(R.id.a_button)
    private val bButton = view.findViewById<Button>(R.id.b_button)
    private val cButton = view.findViewById<Button>(R.id.c_button)
    private val dButton = view.findViewById<Button>(R.id.d_button)

    init {
        aButton.setOnClickListener { currentInfo?.let { onStart.invoke(it.classA) } }
        bButton.setOnClickListener { currentInfo?.let { onStart.invoke(it.classB) } }
        cButton.setOnClickListener { currentInfo?.let { onStart.invoke(it.classC) } }
        dButton.setOnClickListener { currentInfo?.let { onStart.invoke(it.classD) } }
    }

    fun bind(info: ActivityStartInfo) {
        currentInfo = info
        descriptionTextView.text = info.description
    }
}





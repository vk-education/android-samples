package com.example.lecture5.presentation.adapters.flag

import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.example.lecture5.R
import com.example.lecture5.adapters.inflate
import com.example.lecture5.model.Flag

class FlagsAdapter(
    private val flags: List<Flag>,
    private val isSelected: (Flag) -> Boolean,
    private val onSelected: (Boolean, Flag) -> Unit,
) : RecyclerView.Adapter<FlagViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlagViewHolder =
        FlagViewHolder(
            parent.inflate(R.layout.view_holder_flag) as CheckBox,
            isSelected,
            onSelected
        )

    override fun onBindViewHolder(holder: FlagViewHolder, position: Int) {
        holder.bind(flags[position])
    }

    override fun getItemCount(): Int = flags.size
}


class FlagViewHolder(
    private val checkBox: CheckBox,
    private val isSelected: (Flag) -> Boolean,
    private val onSelected: (Boolean, Flag) -> Unit,
) : RecyclerView.ViewHolder(checkBox) {

    private var currentFlag: Flag? = null

    init {
        checkBox.setOnCheckedChangeListener { _, newIsChecked ->
            currentFlag?.let { onSelected.invoke(newIsChecked, it) }
        }
    }

    fun bind(flag: Flag) {
        currentFlag = flag
        checkBox.isChecked = isSelected.invoke(flag)
        checkBox.text = flag.name
    }
}







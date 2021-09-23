package ru.mail.education.lesson02

import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import ru.mail.education.lesson02.databinding.ItemItemBinding

class ItemHolder(val binding: ItemItemBinding, val listener: IListener) : RecyclerView.ViewHolder(binding.root) {
    interface IListener {
        fun onItemClicked(number: Int)
    }

    var item = 0

    init {
        binding.text.setOnClickListener {
            listener.onItemClicked(item)
        }
    }

    fun bind(position: Int) {
        item = position
        binding.text.text = "$position"

        val color = when (position % 2 == 0) {
            true -> Color.BLUE
            false -> Color.RED
        }

        binding.text.setBackgroundColor(color)
    }
}

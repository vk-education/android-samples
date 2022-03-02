package ru.example.myapplication.presentationlayer.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import ru.example.myapplication.R
import ru.example.myapplication.objects.Item

class GifAdapter: ListAdapter<Item, GifHolder>(Calculator()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GifHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_gif, parent, false)

        return GifHolder(view)
    }

    override fun onBindViewHolder(holder: GifHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }


    protected class Calculator: DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.id == newItem.id
        }
    }
}
package ru.hse.lection05.base.presentationlayer.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import ru.hse.lection05.api.giphy.objects.Item
import ru.hse.lection05.base.R

class ItemAdapter(val inflater: LayoutInflater, val listener: IListener) : ListAdapter<Item, ItemHolder>(
    DiffCallback()
) {
    interface IListener: ItemHolder.IListener


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(inflater.inflate(R.layout.item_item, parent, false), listener)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.bind(getItem(position)!!)
    }


    class DiffCallback: DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.url == newItem.url
        }
    }
}
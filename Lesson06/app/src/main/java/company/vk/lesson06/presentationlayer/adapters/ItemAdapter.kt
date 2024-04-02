package company.vk.lesson06.presentationlayer.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import company.vk.lesson06.R

class ItemAdapter(val listener: IListener): ListAdapter<Any, AbstractViewHolder<out Any>>(ItemDiffCallback()) {
    interface IListener: ItemViewHolder.IListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbstractViewHolder<out Any> {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_item, parent, false)
        return ItemViewHolder(view, listener)
    }

    override fun onBindViewHolder(holder: AbstractViewHolder<out Any>, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}
package ru.mail.education.lesson02

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.mail.education.lesson02.databinding.ItemItemBinding

class ItemAdapter(val listener: IListener) : RecyclerView.Adapter<ItemHolder>() {
    interface IListener : ItemHolder.IListener

    protected var count = 100

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val inflater = LayoutInflater.from(parent.context)
        val tmp = ItemItemBinding.inflate(inflater, parent, false)
        return ItemHolder(tmp, listener)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return count
    }

    fun increment() {
        count ++
        notifyItemInserted(count - 1)
    }
}

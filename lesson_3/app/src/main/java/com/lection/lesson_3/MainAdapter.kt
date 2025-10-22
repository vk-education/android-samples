package com.lection.lesson_3

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class MainAdapter() : RecyclerView.Adapter<MainViewHolder>() {

    val items = ArrayList<Item>()

    private fun setItems(items: List<Item>) {
        this.items.clear()
        this.items.addAll(items)
    }

    fun update(items: List<Item>) {
        val differ = MainDifferCallback(this.items, items)
        val result = DiffUtil.calculateDiff(differ)
        setItems(items)
        result.dispatchUpdatesTo(this)
    }

//    fun update(items: List<Item>) {
//        setItems(items)
//        notifyDataSetChanged()
//    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item, null, false)
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(items[position])
    }

}

class MainViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private val title = view.findViewById<TextView>(R.id.text_view)

    fun bind(item: Item) {
        title.text = item.title
    }
}

class MainDifferCallback(val oldItems: List<Item>, val newItems: List<Item>) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldItems.size
    }

    override fun getNewListSize(): Int {
        return newItems.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItems[oldItemPosition].id == newItems[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItems[oldItemPosition].title == newItems[newItemPosition].title
    }

}
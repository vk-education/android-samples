package com.lection.lection2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MainAdapter(private val block: (nick: String) -> Unit) :
    RecyclerView.Adapter<MainViewHolder>() {

    val items: ArrayList<People> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.people_item, null),
            block
        )
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

}

class MainViewHolder(itemView: View, private val block: (nick: String) -> Unit) :
    RecyclerView.ViewHolder(itemView) {

    private val name: TextView = itemView.findViewById(R.id.name_tv)
    private val height: TextView = itemView.findViewById(R.id.height_tv)
    private val button: Button = itemView.findViewById(R.id.button)


    fun bind(item: People) {
        name.text = item.name
        height.text = String.format("%s см", item.height)
        button.text = item.nick
        button.setOnClickListener {
            block.invoke(item.nick)
        }
    }

}
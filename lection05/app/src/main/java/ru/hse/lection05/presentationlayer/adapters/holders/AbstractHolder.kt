package ru.hse.lection05.presentationlayer.adapters.holders

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class AbstractHolder<ITEM>(view: View): RecyclerView.ViewHolder(view) {
    var item: ITEM? = null


    fun bind(newItem: Any?) {
        item = newItem as? ITEM
        bindView(item)
    }

    abstract fun bindView(item: ITEM?)


    protected fun <VIEW: View> findViewById(resourceId: Int) = itemView.findViewById<VIEW>(resourceId)
}
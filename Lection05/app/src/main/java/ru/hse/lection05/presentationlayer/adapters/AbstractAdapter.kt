package ru.hse.lection05.presentationlayer.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import ru.hse.lection05.objects.AbstractObject
import ru.hse.lection05.presentationlayer.adapters.holders.AbstractHolder

abstract class AbstractAdapter(calculator: DiffUtil.ItemCallback<*>)
    : ListAdapter<AbstractObject, AbstractHolder<*>>(calculator as DiffUtil.ItemCallback<AbstractObject>) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbstractHolder<*> {
        throw IllegalArgumentException("Can't handle ViewType: $viewType")
    }

    override fun onBindViewHolder(holder: AbstractHolder<*>, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }


    protected fun inflate(layoutId: Int, view: ViewGroup): View {
        return LayoutInflater.from(view.context).inflate(layoutId, view, false)
    }
}
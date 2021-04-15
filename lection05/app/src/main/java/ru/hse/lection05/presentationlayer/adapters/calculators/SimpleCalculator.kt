package ru.hse.lection05.presentationlayer.adapters.calculators

import androidx.recyclerview.widget.DiffUtil
import ru.hse.lection05.objects.AbstractObject

open class SimpleCalculator<ITEM: AbstractObject>: DiffUtil.ItemCallback<ITEM>() {
    override fun areItemsTheSame(oldItem: ITEM, newItem: ITEM): Boolean {
        return oldItem::class == newItem::class
    }

    override fun areContentsTheSame(oldItem: ITEM, newItem: ITEM): Boolean {
        return oldItem.equals(newItem)
    }
}
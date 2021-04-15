package ru.hse.lection05.presentationlayer.adapters

import android.view.ViewGroup
import ru.hse.lection05.R
import ru.hse.lection05.presentationlayer.adapters.calculators.PlaceCalculator
import ru.hse.lection05.presentationlayer.adapters.holders.AbstractHolder
import ru.hse.lection05.presentationlayer.adapters.holders.SuggestPlaceHolder

class SuggestPlaceAdapter(val listener: IListener): AbstractAdapter(PlaceCalculator()) {
    interface IListener: SuggestPlaceHolder.IListener


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbstractHolder<*> {
        return SuggestPlaceHolder(inflate(R.layout.item_suggest_place, parent), listener)
    }
}
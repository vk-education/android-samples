package ru.hse.lection05.presentationlayer.adapters.holders

import android.view.View
import android.widget.TextView
import ru.hse.lection05.R
import ru.hse.lection05.objects.Place

class SuggestPlaceHolder(view: View, val listener: IListener): AbstractHolder<Place>(view) {
    interface IListener {
        fun onSuggestClicked(item: Place)
    }


    val title by lazy { findViewById<TextView>(R.id.title) }
    val subtitle by lazy { findViewById<TextView>(R.id.subtitle) }


    init {
        itemView.setOnClickListener { listener.onSuggestClicked(item!!) }
    }


    override fun bindView(item: Place?) {
        title.text = item?.name
        subtitle.text = item?.sys?.country
    }
}
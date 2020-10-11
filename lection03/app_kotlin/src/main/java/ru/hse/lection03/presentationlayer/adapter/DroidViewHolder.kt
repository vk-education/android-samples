package ru.hse.lection03.presentationlayer.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.hse.lection03.R
import ru.hse.lection03.objects.Droid

class DroidViewHolder(itemView: View, val listener: IListener) : RecyclerView.ViewHolder(itemView) {
    // Подписчик на события этого холдера
    interface IListener {

        // уведомляение подписчика о том, что был клик по элементу
        fun onDroidClicked(position: Int)
    }


    protected val name: TextView
    protected val image: ImageView


    init {
        // Находим View, которые будут отвечать за имя и картинку
        name = itemView.findViewById(R.id.name)
        image = itemView.findViewById(R.id.image)


        // Отслеживаем клик по элементу
        itemView.setOnClickListener {
            listener.onDroidClicked(adapterPosition)
        }
    }


    fun bind(item: Droid) {
        // Ставим имя дроида
        name.text = item.name

        // Ставим цвет, в зависимости от состояния дроида
        val colorResId = when (item.state) {
            Droid.STATE_REMOVED -> R.color.color_red
            Droid.STATE_NEW -> R.color.color_green
            else -> R.color.color_black
        }
        image.setImageResource(colorResId)
    }
}


package company.vk.lection05.presentationlayer.adapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import company.vk.lection05.R
import company.vk.lection05.objects.Item

class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
	protected val card = view.findViewById<CardView>(R.id.card)
	protected val value = view.findViewById<TextView>(R.id.value)
	protected val image = view.findViewById<ImageView>(R.id.image)

	protected val imageLoader by lazy { Picasso.get() }

	fun bind(item: Item) {
		val color = item.color
		when {
			color == null -> {
				imageLoader.load(item.value).into(image)
				value.text = null
			}

			else -> {
				card.setCardBackgroundColor(color)
				value.text = item.value
			}
		}
	}
}
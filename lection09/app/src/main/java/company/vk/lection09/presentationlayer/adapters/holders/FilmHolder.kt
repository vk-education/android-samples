package company.vk.lection09.presentationlayer.adapters.holders

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import company.vk.base.images.presentationlayer.IImageLoader
import company.vk.base.films.objects.Film
import company.vk.common.ServiceLocator.inject
import company.vk.lection09.R

class FilmHolder(view: View, val listener: IListener): RecyclerView.ViewHolder(view) {
	interface IListener {
		fun onFilmClicked(view: View, item: Film)
	}

	protected val image by lazy { itemView.findViewById<ImageView>(R.id.image) }
	protected val imageLoader by inject<IImageLoader>()

	protected var item: Film? = null


	init {
		itemView.setOnClickListener {
			item?.let { listener.onFilmClicked(itemView, it) }
		}
	}


	fun bind(item: Film) {
		this.item = item

		imageLoader.loadImage(image, item.image)
	}
}
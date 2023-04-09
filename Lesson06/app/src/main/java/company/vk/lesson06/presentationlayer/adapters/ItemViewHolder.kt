package company.vk.Lesson06.presentationlayer.adapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import company.vk.Lesson06.R
import company.vk.Lesson06.ServiceLocator
import company.vk.Lesson06.objects.Item
import company.vk.Lesson06.presentationlayer.images.IImageLoader

class ItemViewHolder(view: View, val listener: IListener): AbstractViewHolder<Item>(view) {
	interface IListener {
		fun onItemClicked(item: Item)
	}

	protected val imageLoader by lazy { ServiceLocator.inject<IImageLoader>() }

	protected val image by lazy { itemView.findViewById<ImageView>(R.id.image) }
	protected val title by lazy { itemView.findViewById<TextView>(R.id.title) }
	protected val species by lazy { itemView.findViewById<TextView>(R.id.species) }

	init {
		itemView.setOnClickListener {
			item?.let(listener::onItemClicked)
		}
	}

	override fun bindView(item: Item?) {
		super.bindView(item)

		imageLoader.loadImage(image, item?.image)
		title.text = item?.name
		species.text = item?.species
	}
}
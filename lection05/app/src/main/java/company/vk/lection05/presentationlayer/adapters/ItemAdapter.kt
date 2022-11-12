package company.vk.lection05.presentationlayer.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import company.vk.lection05.R
import company.vk.lection05.objects.Item

class ItemAdapter: ListAdapter<Item, ItemViewHolder>(ItemDifferCallback()) {
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
		val view = LayoutInflater.from(parent.context).inflate(R.layout.item_item, parent, false)
		return ItemViewHolder(view)
	}

	override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
		val item = getItem(position)
		holder.bind(item)
	}
}
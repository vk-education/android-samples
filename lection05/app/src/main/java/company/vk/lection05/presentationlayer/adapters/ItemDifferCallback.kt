package company.vk.lection05.presentationlayer.adapters

import androidx.recyclerview.widget.DiffUtil
import company.vk.lection05.objects.Item

class ItemDifferCallback: DiffUtil.ItemCallback<Item>() {
	override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
		return oldItem.id == newItem.id
	}

	override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
		return oldItem.value == newItem.value
	}
}
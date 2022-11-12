package company.vk.myapplication.ui.main

import androidx.recyclerview.widget.DiffUtil
import company.vk.myapplication.objects.Cat

class CatDiffitemCallback: DiffUtil.ItemCallback<Cat>() {
	override fun areItemsTheSame(oldItem: Cat, newItem: Cat): Boolean {
		return oldItem.id == newItem.id
	}

	override fun areContentsTheSame(oldItem: Cat, newItem: Cat): Boolean {
		return oldItem.imageId() == newItem.imageId()
	}
}
package company.vk.myapplication.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import company.vk.myapplication.R
import company.vk.myapplication.objects.Cat

class CatAdapter: ListAdapter<Cat, CatViewHolder>(CatDiffitemCallback()) {
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
		val view = LayoutInflater.from(parent.context).inflate(R.layout.item_item, parent, false)
		return CatViewHolder(view)
	}

	override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
		val cat = getItem(position)
		holder.bind(cat)
	}
}
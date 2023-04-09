package company.vk.Lesson06.presentationlayer.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder

abstract class AbstractViewHolder<T: Any>(view: View) : ViewHolder(view) {
	var item: T? = null

	fun bind(item: Any?) {
		this.item = item as? T

		bindView(this.item)
	}

	protected open fun bindView(item: T?) {

	}
}
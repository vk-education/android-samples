package company.vk.lesson06.presentationlayer.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class AbstractViewHolder<T: Any>(view: View) : RecyclerView.ViewHolder(view) {
    var item: T? = null

    fun bind(item: Any?) {
        this.item = item as? T

        bindView(this.item)
    }

    protected open fun bindView(item: T?) {

    }
}
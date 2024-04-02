package company.vk.lesson06.presentationlayer.adapters

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import company.vk.lesson06.objects.Item

class ItemDiffCallback: DiffUtil.ItemCallback<Any>() {
    @SuppressLint("DiffUtilEquals")
    override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
        return when {
            oldItem is Item && newItem is Item -> oldItem.id == newItem.id
            else -> oldItem == newItem
        }
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
        return oldItem == newItem
    }
}
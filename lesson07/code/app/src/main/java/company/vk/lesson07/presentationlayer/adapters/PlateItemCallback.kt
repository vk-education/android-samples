package company.vk.lesson07.presentationlayer.adapters

import androidx.recyclerview.widget.DiffUtil
import company.vk.lesson07.objects.Plate

class PlateItemCallback: DiffUtil.ItemCallback<Plate>() {
    override fun areItemsTheSame(oldItem: Plate, newItem: Plate): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Plate, newItem: Plate): Boolean {
        return oldItem == newItem
    }
}

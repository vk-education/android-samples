package company.vk.lesson07.presentationlayer.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import company.vk.lesson07.R
import company.vk.lesson07.objects.Plate

class PlateAdapter: ListAdapter<Plate, PlateViewHolder>(PlateItemCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlateViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_plate, parent, false)

        return PlateViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlateViewHolder, position: Int) {
        val plate = getItem(position)
        holder.bind(plate)
    }
}
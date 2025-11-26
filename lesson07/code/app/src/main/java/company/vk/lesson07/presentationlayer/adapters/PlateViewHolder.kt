package company.vk.lesson07.presentationlayer.adapters

import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import company.vk.lesson07.R
import company.vk.lesson07.objects.Plate

class PlateViewHolder(view: View): ViewHolder(view) {
    protected val plateView by lazy { view.findViewById<TextView>(R.id.plate) }

    fun bind(plate: Plate?) {
        plateView.text = plate?.value?: "NONE"
        plateView.setBackgroundColor(plate?.color?: Color.BLACK)
    }
}

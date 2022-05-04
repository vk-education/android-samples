package company.vk.lection09.presentationlayer.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import company.vk.base.films.objects.Film
import company.vk.lection09.R
import company.vk.lection09.presentationlayer.adapters.holders.FilmHolder

class FilmAdapter(val listener: IListener): ListAdapter<Film, FilmHolder>(Calculator()) {
	interface IListener: FilmHolder.IListener


	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmHolder {
		val view = LayoutInflater.from(parent.context)
			.inflate(R.layout.item_film, parent, false)

		return FilmHolder(view, listener)
	}

	override fun onBindViewHolder(holder: FilmHolder, position: Int) {
		val item = getItem(position)
		holder.bind(item)
	}


	protected class Calculator: DiffUtil.ItemCallback<Film>() {
		override fun areItemsTheSame(oldItem: Film, newItem: Film): Boolean {
			return oldItem.id == newItem.id
		}

		override fun areContentsTheSame(oldItem: Film, newItem: Film): Boolean {
			return oldItem.id == newItem.id
		}
	}
}
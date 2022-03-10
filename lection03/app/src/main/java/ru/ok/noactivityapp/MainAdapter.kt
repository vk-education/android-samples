package ru.ok.noactivityapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class MainAdapter(
    private val movies: List<Movie>,
) : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_movie, null)
        return MainViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivPoster: ImageView = itemView.findViewById(R.id.view_movie__iv_poster)
        val tvTitle: TextView = itemView.findViewById(R.id.view_movie_tv_title)
        val tvDescription: TextView = itemView.findViewById(R.id.view_movie_tv_description)

        init {
            itemView.setOnClickListener {
                Toast.makeText(
                    itemView.context,
                    tvTitle.text,
                    Toast.LENGTH_SHORT,
                ).show()
            }
        }

        fun bind(movie: Movie) {
            ivPoster.setImageResource(movie.img)
            tvTitle.text = movie.title
            tvDescription.text = movie.description
        }
    }
}
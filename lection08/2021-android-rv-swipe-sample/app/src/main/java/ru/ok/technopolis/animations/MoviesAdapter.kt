package ru.ok.technopolis.animations

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import ru.ok.technopolis.animations.MoviesAdapter.MovieViewHolder

class MoviesAdapter(private val movies: MutableList<Movie>) : RecyclerView.Adapter<MovieViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MovieViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.movie_item, viewGroup, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: MovieViewHolder, i: Int) {
        val movie = movies[i]
        viewHolder.bind(movie)
        viewHolder.itemView.tag = movie
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    fun removeItem(position: Int) {
        movies.removeAt(position)
        notifyItemRemoved(position)
    }

    class MovieViewHolder(itemView: View) : ViewHolder(itemView) {

        private val nameTextView: TextView = itemView.findViewById(R.id.movie_item__tv_name)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.movie_item__tv_description)
        private val posterImageView: ImageView = itemView.findViewById(R.id.movie_item__iv_poster)

        fun bind(movie: Movie) {
            nameTextView.text = movie.name
            descriptionTextView.text = movie.description
            posterImageView.setImageResource(movie.poster)
        }


    }
}
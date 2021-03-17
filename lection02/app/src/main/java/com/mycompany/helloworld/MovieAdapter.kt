package com.mycompany.helloworld

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class MovieAdapter(private val movies: List<Movie>) :
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie)
    }

    override fun getItemCount(): Int {
        println("Movies size = ${movies.size}")
        return movies.size
    }

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val tvName: TextView = itemView.findViewById(R.id.movie_item__tv_name)
        private val tvDescription: TextView = itemView.findViewById(R.id.movie_item__tv_description)
        private val ivPoster: ImageView = itemView.findViewById(R.id.movie_item__iv_poster)

        init {
            itemView.setOnClickListener {
                Toast.makeText(
                    itemView.context,
                    tvName.text,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        fun bind(movie: Movie) {
            tvName.text = movie.title
            tvDescription.text = movie.description
            ivPoster.setImageResource(movie.picture)
        }

    }


}
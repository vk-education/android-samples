package com.otopba.hello_world

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter

class MovieAdapter(private val movies: List<Movie>) : Adapter<MovieAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    override fun getItemCount() = movies.size

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener {
                Toast.makeText(
                    itemView.context,
                    title.text,
                    Toast.LENGTH_SHORT
                ).show()
            }

        }

        private val poster: ImageView = itemView.findViewById(R.id.iv)
        private val title: TextView = itemView.findViewById(R.id.tv_title)
        private val description: TextView = itemView.findViewById(R.id.tv_description)

        fun bind(movie: Movie) {
            poster.setImageResource(movie.image)
            title.text = movie.title
            description.text = movie.description
        }

    }
}
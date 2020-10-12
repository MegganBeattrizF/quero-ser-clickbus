package com.clickbus.moviesdbtest.controller

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.clickbus.moviesdbtest.R
import com.clickbus.moviesdbtest.movies.models.Movie

class MoviesAdapter(
    private var movies: List<Movie>
) : RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.recycler_item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    fun updateMovies(movies: List<Movie>) {
        this.movies = movies
        notifyDataSetChanged()
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val poster: ImageView = itemView.findViewById(R.id.item_movie_poster)
        private val title: TextView = itemView.findViewById(R.id.textView_title)
        private val originTitle: TextView = itemView.findViewById(R.id.textView_originTitle)
        private val vote: TextView = itemView.findViewById(R.id.textView_qtdVotos)
        private val vote_average: TextView = itemView.findViewById(R.id.textView_media)
        private val ratingBar: RatingBar = itemView.findViewById(R.id.ratingBar_popularity)

        fun bind(movie: Movie) {

            ratingBar.rating = (movie.voteCount/2).toFloat()
            vote_average.text = movie.voteAverage.toString()
            vote.text = movie.voteCount.toString()
            title.text = movie.originalTitle
            originTitle.text = movie.title
            Glide.with(itemView)
                .load("https://image.tmdb.org/t/p/w342${movie.posterPath}")
                .transform(CenterCrop())
                .into(poster)
        }
    }
}
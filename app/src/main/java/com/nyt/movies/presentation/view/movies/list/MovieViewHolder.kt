package com.nyt.movies.presentation.view.movies.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.nyt.movies.R
import com.nyt.movies.databinding.ItemMovieBinding
import com.nyt.movies.domain.entity.movie.Movie

class MovieViewHolder(
    private var binding: ItemMovieBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun setupBinding(movie: Movie, callback: (Movie) -> Unit) {
        with(binding) {
            root.setOnClickListener { callback(movie) }
            val requestOptions = RequestOptions()
            requestOptions.centerCrop()
            Glide.with(root.context)
                .load(movie.multimedia?.src)
                .apply(requestOptions)
                .into(imageViewMoviePoster)
            textViewMovieTitle.text = movie.displayTitle
        }
    }

    companion object {
        fun inflate(parent: ViewGroup?) = MovieViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent?.context),
                R.layout.item_movie,
                parent,
                false
            )
        )
    }
}
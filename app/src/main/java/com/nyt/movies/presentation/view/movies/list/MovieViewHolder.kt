package com.nyt.movies.presentation.view.movies.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.nyt.movies.R
import com.nyt.movies.databinding.ItemMovieBinding
import com.nyt.movies.domain.entity.movie.Movie
import com.nyt.movies.presentation.util.extension.load
import com.nyt.movies.presentation.util.extension.setSafeClickListener

class MovieViewHolder(
    private var binding: ItemMovieBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun setupBinding(
        movie: Movie,
        callback: (Movie) -> Unit,
        onLikeClickedCallback: (Movie) -> Unit,
        onShareClickedCallback: (Movie) -> Unit
    ) {
        with(binding) {
            root.setOnClickListener { callback(movie) }
            imageViewMoviePoster.load(movie.multimedia?.src)
            textViewMovieTitle.text = movie.displayTitle
            textViewMovieReview.text = movie.summaryShort
            buttonLike.setSafeClickListener { onLikeClickedCallback(movie) }
            buttonShare.setSafeClickListener { onShareClickedCallback(movie) }
            setupLike(movie)
        }
    }

    private fun setupLike(movie: Movie) {
        binding.buttonLike.setImageDrawable(
            ContextCompat.getDrawable(
                binding.root.context,
                if (movie.isFavorite) R.drawable.ic_favorite else R.drawable.ic_favorite_border
            )
        )
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
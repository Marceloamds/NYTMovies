package com.nyt.movies.presentation.view.movies.list

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.nyt.movies.domain.entity.movie.Movie

class ListMoviesAdapter(
    private val callback: (Movie) -> Unit
) : ListAdapter<Movie, MovieViewHolder>(DiffUtilCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder.inflate(parent)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.setupBinding(getItem(position), callback)
    }

    companion object DiffUtilCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie) =
            oldItem.displayTitle == newItem.displayTitle

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie) =
            oldItem == newItem
    }
}
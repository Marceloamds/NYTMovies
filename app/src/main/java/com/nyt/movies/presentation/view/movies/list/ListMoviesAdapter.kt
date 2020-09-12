package com.nyt.movies.presentation.view.movies.list

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.nyt.movies.domain.entity.movie.Movie
import com.nyt.movies.presentation.util.paging.PagingListAdapter

class ListMoviesAdapter(
    private val callback: (Movie) -> Unit,
    onProgressShownCallBack: () -> Unit
) : PagingListAdapter<Movie, MovieViewHolder>(DiffUtilCallback, onProgressShownCallBack) {

    override fun onCreateSubViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder.inflate(parent)
    }

    override fun onBindSubViewHolder(holder: MovieViewHolder, position: Int) {
        holder.setupBinding(getItem(position), callback)
    }

    companion object DiffUtilCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie) =
            oldItem.displayTitle == newItem.displayTitle

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie) =
            oldItem == newItem
    }
}
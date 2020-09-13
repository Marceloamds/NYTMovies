package com.nyt.movies.presentation.view.movies.list

import android.view.ViewGroup
import com.nyt.movies.domain.entity.movie.Movie
import com.nyt.movies.presentation.util.paging.PagingListAdapter

class ListMoviesAdapter(
    private val onMovieClickedCallback: (Movie) -> Unit,
    private val onLikeClickedCallback: (Movie) -> Unit,
    private val onShareClickedCallback: (Movie) -> Unit,
    onProgressShownCallback: () -> Unit?
) : PagingListAdapter<Movie, MovieViewHolder>(onProgressShownCallback) {


    override fun onCreateSubViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder.inflate(parent)
    }

    override fun onBindSubViewHolder(holder: MovieViewHolder, position: Int) {
        holder.setupBinding(
            shownList[position],
            onMovieClickedCallback,
            onLikeClickedCallback,
            onShareClickedCallback
        )
    }

    fun updateMovie(movie: Movie) {
        val movieIndex = shownList.indexOfFirst { movie.link.url == it.link.url }
        notifyItemChanged(movieIndex)
    }
}
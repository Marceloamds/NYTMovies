package com.nyt.movies.presentation.view.movies.list

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.nyt.movies.domain.entity.currency.Currency

class ListMoviesAdapter(
    private val callback: (Currency) -> Unit
) : ListAdapter<Currency, MovieViewHolder>(DiffUtilCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder.inflate(parent)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.setupBinding(getItem(position), callback)
    }

    companion object DiffUtilCallback : DiffUtil.ItemCallback<Currency>() {
        override fun areItemsTheSame(oldItem: Currency, newItem: Currency) =
            oldItem.code == newItem.code

        override fun areContentsTheSame(oldItem: Currency, newItem: Currency) =
            oldItem == newItem
    }
}
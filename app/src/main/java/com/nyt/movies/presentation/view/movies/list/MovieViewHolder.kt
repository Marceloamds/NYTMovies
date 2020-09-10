package com.nyt.movies.presentation.view.movies.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.nyt.movies.R
import com.nyt.movies.databinding.ItemCurrencyBinding
import com.nyt.movies.domain.entity.currency.Currency

class MovieViewHolder(
    private var binding: ItemCurrencyBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun setupBinding(item: Currency, callback: (Currency) -> Unit) {
        with(binding) {
            textViewCurrencyName.text = item.getFormattedString(root.context)
            root.setOnClickListener { callback(item) }
        }
    }

    companion object {
        fun inflate(parent: ViewGroup?) = MovieViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent?.context),
                R.layout.item_currency,
                parent,
                false
            )
        )
    }
}
package com.nyt.movies.presentation.view.movies.list

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.PopupMenu
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import com.nyt.movies.R
import com.nyt.movies.databinding.ActivityListCurrenciesBinding
import com.nyt.movies.domain.entity.movie.Movie
import com.nyt.movies.presentation.util.base.BaseActivity
import com.nyt.movies.presentation.util.base.BaseViewModel
import com.nyt.movies.presentation.util.query.QueryChangesHelper
import com.nyt.movies.presentation.view.movies.MovieFilterType
import org.koin.android.viewmodel.ext.android.viewModel

class ListMoviesActivity : BaseActivity() {

    override val baseViewModel: BaseViewModel get() = _viewModel
    private val _viewModel: ListMoviesViewModel by viewModel()

    private lateinit var binding: ActivityListCurrenciesBinding
    private lateinit var adapter: ListMoviesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_list_currencies)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.search_for_movie)
        setupAdapter()
    }

    override fun subscribeUi() {
        super.subscribeUi()
        _viewModel.moviesList.observe(this, ::onMoviesListReceived)
        _viewModel.placeholder.observe(this) { binding.placeholderView.setPlaceholder(it) }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.list_currencies_menu, menu)
        setupSearchView(menu?.findItem(R.id.action_search))
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.filter_by_name -> {
                _viewModel.filterFullList(MovieFilterType.FilterByName)
                true
            }
            R.id.filter_by_code -> {
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupAdapter() {
        adapter = ListMoviesAdapter(::onMovieSelected)
        binding.recyclerViewCurrencies.adapter = adapter
    }

    private fun onMoviesListReceived(moviesList: List<Movie>?) {
        moviesList?.let(adapter::submitList)
    }

    private fun setupSearchView(searchItem: MenuItem?) {
        searchItem?.let {
            val searchView = it.actionView as SearchView
            searchView.setOnQueryTextListener(QueryChangesHelper(_viewModel::onQueryChanged))
            searchView.setOnSearchClickListener { showQueryPopUpMenu(searchView) }
        }
    }

    private fun showQueryPopUpMenu(searchView: SearchView) {
        val popUpMenu = PopupMenu(this, searchView)
        popUpMenu.inflate(R.menu.filter_currency_menu)
        popUpMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.by_name -> {
                    _viewModel.queryFilterType = MovieFilterType.FilterByName
                    true
                }
                R.id.by_code -> {
                    true
                }
                else -> {
                    false
                }
            }
        }
        popUpMenu.show()
    }

    private fun onMovieSelected(movie: Movie) {
        // goTo Movie Details
    }

    companion object {
        const val CURRENCY_EXTRA = "CURRENCY_EXTRA"

        fun createIntent(context: Context) = Intent(context, ListMoviesActivity::class.java)
    }
}


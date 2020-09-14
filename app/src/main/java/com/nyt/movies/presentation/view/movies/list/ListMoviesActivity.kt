package com.nyt.movies.presentation.view.movies.list

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.nyt.movies.R
import com.nyt.movies.databinding.ActivityListMoviesBinding
import com.nyt.movies.presentation.util.base.BaseActivity
import com.nyt.movies.presentation.util.base.BaseViewModel
import com.nyt.movies.presentation.util.extension.setSafeClickListener
import com.nyt.movies.presentation.util.extension.shareMovie
import com.nyt.movies.presentation.util.query.QueryChangesHelper
import com.nyt.movies.presentation.view.movies.OnMovieChangedObservable
import org.koin.android.viewmodel.ext.android.viewModel

class ListMoviesActivity : BaseActivity() {

    override val baseViewModel: BaseViewModel get() = _viewModel
    private val _viewModel: ListMoviesViewModel by viewModel()

    private lateinit var binding: ActivityListMoviesBinding
    private lateinit var adapter: ListMoviesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_list_movies)
        supportActionBar?.title = getString(R.string.search_for_reviews)
        setupRecyclerView()
        setupUi()
    }

    override fun subscribeUi() {
        super.subscribeUi()
        _viewModel.moviesList.observe(this) { it?.let(adapter::submitList) }
        _viewModel.progressVisible.observe(this) { it?.let(adapter::setProgressVisible) }
        _viewModel.placeholder.observe(this) { binding.placeholderView.setPlaceholder(it) }
        _viewModel.showEmptyPlaceholder.observe(this, ::onEmptyPlaceholder)
        _viewModel.shareMovie.observe(this, ::shareMovie)
        OnMovieChangedObservable.updatedMovie.observe(this) { it?.let(adapter::updateMovie) }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.list_movies_menu, menu)
        setupSearchView(menu?.findItem(R.id.action_search))
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.filter_favorite -> {
                _viewModel.onFavoriteMoviesClicked()
                true
            }
            R.id.all_movies -> {
                _viewModel.getAllMovies()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    private fun setupRecyclerView() {
        adapter = ListMoviesAdapter(
            _viewModel::onMovieSelected,
            _viewModel::onLikeClicked,
            _viewModel::onShareClicked,
            _viewModel::onProgressItemShown
        )
        binding.recyclerViewMovies.layoutManager = LinearLayoutManager(this@ListMoviesActivity)
        binding.recyclerViewMovies.adapter = adapter
    }

    private fun setupUi() {
        binding.buttonTryAgain.setSafeClickListener { _viewModel.getAllMovies() }
    }

    private fun setupSearchView(searchItem: MenuItem?) {
        searchItem?.let {
            val searchView = it.actionView as SearchView
            searchView.setOnQueryTextListener(QueryChangesHelper(_viewModel::onQueryChanged))
            searchView.setOnCloseListener(::onQueryClosed)
        }
    }

    private fun onQueryClosed(): Boolean {
        _viewModel.onQueryChanged("")
        return true
    }

    private fun onEmptyPlaceholder(showEmptyPlaceholder: Boolean?) {
        binding.emptyListLayout.visibility =
            if (showEmptyPlaceholder == true) View.VISIBLE else View.GONE
    }

    companion object {

        fun createIntent(context: Context) =
            Intent(context, ListMoviesActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
    }
}


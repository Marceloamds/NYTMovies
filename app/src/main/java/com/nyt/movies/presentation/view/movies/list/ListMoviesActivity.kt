package com.nyt.movies.presentation.view.movies.list

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.nyt.movies.R
import com.nyt.movies.databinding.ActivityListMoviesBinding
import com.nyt.movies.domain.entity.movie.Movie
import com.nyt.movies.presentation.util.base.BaseActivity
import com.nyt.movies.presentation.util.base.BaseViewModel
import com.nyt.movies.presentation.util.query.QueryChangesHelper
import org.koin.android.viewmodel.ext.android.viewModel

class ListMoviesActivity : BaseActivity() {

    override val baseViewModel: BaseViewModel get() = _viewModel
    private val _viewModel: ListMoviesViewModel by viewModel()

    private lateinit var binding: ActivityListMoviesBinding
    private lateinit var adapter: ListMoviesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_list_movies)
        supportActionBar?.title = getString(R.string.search_for_movie)
        setupRecyclerView()
    }

    override fun subscribeUi() {
        super.subscribeUi()
        _viewModel.moviesList.observe(this, ::onMoviesListReceived)
        _viewModel.progressVisible.observe(this, { it?.let(adapter::setProgressVisible) })
        _viewModel.shareMovie.observe(this, ::onShareMovie)
        _viewModel.placeholder.observe(this) { binding.placeholderView.setPlaceholder(it) }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.list_movies_menu, menu)
        setupSearchView(menu?.findItem(R.id.action_search))
        return super.onCreateOptionsMenu(menu)
    }

    private fun setupRecyclerView() {
        adapter = ListMoviesAdapter(
            _viewModel::onMovieSelected,
            _viewModel::onLikeClicked,
            _viewModel::onShareClicked,
            _viewModel::onProgressItemShown
        )
        with(binding) {
            recyclerViewMovies.layoutManager = LinearLayoutManager(this@ListMoviesActivity)
            recyclerViewMovies.adapter = adapter
        }
    }

    private fun onMoviesListReceived(moviesList: List<Movie>?) {
        moviesList?.let(adapter::submitList)
    }

    private fun onShareMovie(movie: Movie?) {
        val shareIntent = Intent()
        with(shareIntent) {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "SE LIGA NO FILMASSO! ${movie?.link?.url}")
            type = "text/plain"
        }
        startActivity(Intent.createChooser(shareIntent, getString(R.string.send_movie_to)))
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

    companion object {

        fun createIntent(context: Context) = Intent(context, ListMoviesActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
    }
}


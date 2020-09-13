package com.nyt.movies.presentation.view.movies.list

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nyt.movies.domain.entity.movie.Movie
import com.nyt.movies.domain.entity.movie.MoviesList
import com.nyt.movies.domain.interactor.GetMoviesList
import com.nyt.movies.domain.interactor.LikeMovie
import com.nyt.movies.presentation.util.base.BaseViewModel
import com.nyt.movies.presentation.view.movies.details.MovieDetailsNavData

class ListMoviesViewModel constructor(
    private val getMoviesList: GetMoviesList,
    private val likeMovie: LikeMovie,
    private val context: Context
) : BaseViewModel() {

    val moviesList: LiveData<List<Movie>> get() = _moviesList
    val updateMovie: LiveData<Movie> get() = _updateMovie
    val progressVisible: LiveData<Boolean> get() = _progressVisible
    val shareMovie: LiveData<Movie> get() = _shareMovie

    private val _moviesList by lazy { MutableLiveData<List<Movie>>() }
    private val _updateMovie by lazy { MutableLiveData<Movie>() }
    private val _progressVisible by lazy { MutableLiveData<Boolean>() }
    private val _shareMovie by lazy { MutableLiveData<Movie>() }

    private var currentPage: Int = 0
    private var currentQuery = ""

    init {
        requestNewMovies()
    }

    fun onQueryChanged(query: String) {
        currentQuery = query
        currentPage = 0
        requestNewMovies()
    }

    fun onMovieSelected(movie: Movie) {
        goTo(MovieDetailsNavData(movie))
    }

    fun onLikeClicked(movie: Movie) {
        launchDataLoad {
            _updateMovie.value = likeMovie.execute(movie)
        }
    }

    fun onShareClicked(movie: Movie) {
        _shareMovie.value = movie
    }

    fun onProgressItemShown() {
        currentPage += 1
        requestNewMovies(false)
    }

    private fun requestNewMovies(showPlaceholder: Boolean = true) {
        launchDataLoad(showPlaceholder, onFailure = ::onFailure) {
            val moviesList = getMoviesList.execute(currentPage, currentQuery)
            setMoviesList(moviesList)
            moviesList?.exception?.let { setDialog(it) { requestNewMovies(showPlaceholder) } }
        }
    }

    private fun setMoviesList(moviesList: MoviesList?) {
        _progressVisible.value = moviesList?.hasMore
        _moviesList.value = moviesList?.movies
    }

    private fun onFailure(throwable: Throwable) {
        setDialog(throwable) { requestNewMovies(true) }
    }
}
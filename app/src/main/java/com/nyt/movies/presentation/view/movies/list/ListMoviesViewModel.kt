package com.nyt.movies.presentation.view.movies.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nyt.movies.domain.entity.movie.Movie
import com.nyt.movies.domain.entity.movie.MoviesList
import com.nyt.movies.domain.interactor.GetFavoriteMoviesList
import com.nyt.movies.domain.interactor.GetMoviesList
import com.nyt.movies.domain.interactor.LikeMovie
import com.nyt.movies.presentation.util.base.BaseViewModel
import com.nyt.movies.presentation.view.movies.OnMovieChangedObservable
import com.nyt.movies.presentation.view.movies.details.MovieDetailsNavData

class ListMoviesViewModel constructor(
    private val getMoviesList: GetMoviesList,
    private val likeMovie: LikeMovie,
    private val getFavoriteMoviesList: GetFavoriteMoviesList
) : BaseViewModel() {

    val moviesList: LiveData<List<Movie>> get() = _moviesList
    val progressVisible: LiveData<Boolean> get() = _progressVisible
    val showEmptyPlaceholder: LiveData<Boolean> get() = _showEmptyPlaceholder
    val shareMovie: LiveData<Movie> get() = _shareMovie

    private val _moviesList by lazy { MutableLiveData<List<Movie>>() }
    private val _progressVisible by lazy { MutableLiveData<Boolean>() }
    private val _showEmptyPlaceholder by lazy { MutableLiveData<Boolean>() }
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
            OnMovieChangedObservable.updateMovie(likeMovie.execute(movie))
        }
    }

    fun onShareClicked(movie: Movie) {
        _shareMovie.value = movie
    }

    fun onFavoriteMoviesClicked() {
        currentPage = 0
        launchDataLoad(onFailure = ::onFailure) {
            setMoviesList(getFavoriteMoviesList.execute(currentPage))
        }
    }

    fun getAllMovies() {
        currentPage = 0
        requestNewMovies()
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
        _showEmptyPlaceholder.value = moviesList?.movies?.isEmpty()
    }

    private fun onFailure(throwable: Throwable) {
        setDialog(throwable) { requestNewMovies(true) }
    }
}
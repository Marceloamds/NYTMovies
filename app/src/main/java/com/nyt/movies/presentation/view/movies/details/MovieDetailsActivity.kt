package com.nyt.movies.presentation.view.movies.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.nyt.movies.R
import com.nyt.movies.databinding.ActivityMoviesDetailsBinding
import com.nyt.movies.domain.entity.movie.Movie
import com.nyt.movies.presentation.util.base.BaseActivity
import com.nyt.movies.presentation.util.base.BaseViewModel
import com.nyt.movies.presentation.util.extension.*
import com.nyt.movies.presentation.view.movies.OnMovieChangedObservable
import org.koin.android.viewmodel.ext.android.viewModel

class MovieDetailsActivity : BaseActivity() {

    override val baseViewModel: BaseViewModel get() = _viewModel
    private val _viewModel: MovieDetailsViewModel by viewModel()

    private lateinit var binding: ActivityMoviesDetailsBinding
    private val intentMovie by lazy { intent.getSerializableExtra(MOVIE_EXTRA) as Movie }
    private var movie: Movie? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movies_details)
        movie = intentMovie
        setupMovieInfo()
        setupLike()
        setupUi()
    }

    override fun subscribeUi() {
        super.subscribeUi()
        _viewModel.shareMovie.observe(this, ::shareMovie)
        OnMovieChangedObservable.updatedMovie.observe(this, ::onMovieChanged)
    }

    private fun setupUi() {
        with(binding) {
            buttonGoToReview.setSafeClickListener { movie?.link?.url?.let { openBrowser(it) } }
            buttonFavorite.setSafeClickListener { _viewModel.onLikeClicked(movie) }
            buttonShare.setSafeClickListener { _viewModel.onShareClicked(movie) }
            buttonGoBack.setSafeClickListener { finish() }
        }
    }

    private fun setupMovieInfo() {
        with(binding) {
            imageViewMoviePoster.load(movie?.multimedia?.src)
            textViewMovieTitle.text = movie?.displayTitle
            textViewSynopsis.text = movie?.summaryShort
            textViewReviewTitle.text = movie?.headline
            textViewPublication.text = getString(
                R.string.review_publication_template,
                movie?.byline,
                movie?.publicationDate?.format()
            )
            textViewOpeningDate.text = movie?.openingDate?.format()
        }
    }

    private fun onMovieChanged(movie: Movie) {
        this.movie = movie
        setupLike()
    }

    private fun setupLike() {
        binding.buttonFavorite.setImageDrawable(
            ContextCompat.getDrawable(
                binding.root.context,
                if (movie?.isFavorite == true) R.drawable.ic_favorite else R.drawable.ic_favorite_border
            )
        )
    }

    companion object {
        private const val MOVIE_EXTRA = "MOVIE_EXTRA"

        fun createIntent(context: Context, movie: Movie): Intent {
            return Intent(context, MovieDetailsActivity::class.java).apply {
                putExtra(MOVIE_EXTRA, movie)
            }
        }
    }
}
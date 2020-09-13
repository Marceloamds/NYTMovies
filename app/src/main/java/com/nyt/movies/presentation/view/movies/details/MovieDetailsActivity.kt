package com.nyt.movies.presentation.view.movies.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.nyt.movies.R
import com.nyt.movies.databinding.ActivityMoviesDetailsBinding
import com.nyt.movies.domain.entity.movie.Movie
import com.nyt.movies.presentation.util.base.BaseActivity
import com.nyt.movies.presentation.util.base.BaseViewModel
import com.nyt.movies.presentation.util.extension.format
import com.nyt.movies.presentation.util.extension.load
import com.nyt.movies.presentation.util.extension.openBrowser
import org.koin.android.viewmodel.ext.android.viewModel

class MovieDetailsActivity : BaseActivity() {

    override val baseViewModel: BaseViewModel get() = _viewModel
    private val _viewModel: MovieDetailsViewModel by viewModel()

    private lateinit var binding: ActivityMoviesDetailsBinding
    private val movie by lazy { intent.getSerializableExtra(MOVIE_EXTRA) as Movie }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movies_details)
        setupImage()
        setupUi()
    }

    private fun setupUi() {
        with(binding) {
            textViewMovieTitle.text = movie.displayTitle
            textViewSynopsis.text = movie.summaryShort
            textViewReviewTitle.text = movie.headline
            textViewPublication.text = getString(
                R.string.review_publication_template,
                movie.byline,
                movie.publicationDate.format()
            )
            textViewOpeningDate.text = movie.openingDate?.format()
            buttonGoToReview.setOnClickListener { movie.link.url?.let { openBrowser(it) } }
            buttonGoBack.setOnClickListener { finish() }
        }
    }

    private fun setupImage(){
        binding.imageViewMoviePoster.load(movie.multimedia?.src)
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
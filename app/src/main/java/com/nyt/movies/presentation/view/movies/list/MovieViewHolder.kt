package com.nyt.movies.presentation.view.movies.list

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.nyt.movies.R
import com.nyt.movies.databinding.ItemMovieBinding
import com.nyt.movies.domain.entity.movie.Movie
import com.nyt.movies.presentation.util.extension.format
import com.nyt.movies.presentation.util.extension.load
import com.nyt.movies.presentation.util.extension.openBrowser
import com.nyt.movies.presentation.util.extension.setSafeClickListener

class MovieViewHolder(
    private var binding: ItemMovieBinding
) : RecyclerView.ViewHolder(binding.root) {

    private var isExpanded = false

    fun setupBinding(
        movie: Movie,
        callback: (Movie) -> Unit,
        onLikeClickedCallback: (Movie) -> Unit,
        onShareClickedCallback: (Movie) -> Unit
    ) {
        with(binding) {
            isExpanded = false
            motionLayout.transitionToStart()
//            motionLayout.addTransitionListener(TransitionListener(binding, movie))
            motionLayout.setSafeClickListener(400) { startAnimation(movie) }
            imageViewMoviePoster.load(movie.multimedia?.src)
            textViewMovieReview.text = movie.summaryShort
            textViewMovieTitle.text = movie.displayTitle
            textViewMovieTitleEnd.text = movie.displayTitle
            textViewSynopsis.text = movie.summaryShort
            textViewReviewTitle.text = movie.headline
            textViewPublication.text = binding.root.context.getString(
                R.string.review_publication_template,
                movie.byline,
                movie.publicationDate.format()
            )
            textViewOpeningDate.text = movie.openingDate?.format()
            buttonFavorite.setSafeClickListener(200) { onLikeClicked(movie, onLikeClickedCallback) }
            buttonShare.setSafeClickListener { onShareClickedCallback(movie) }
            buttonGoToReview.setSafeClickListener { binding.root.context.openBrowser(movie.link.url) }
            setupLike(movie)
        }
    }

    private fun onLikeClicked(movie: Movie, onLikeClickedCallback: (Movie) -> Unit) {
        movie.isFavorite = !movie.isFavorite
        setupLike(movie)
        onLikeClickedCallback(movie)
    }

    private fun startAnimation(movie: Movie) {
        if (isExpanded) binding.motionLayout.transitionToStart()
        else binding.motionLayout.transitionToEnd()
        isExpanded = !isExpanded
    }

    private fun setupLike(movie: Movie) {
        binding.buttonFavorite.setImageDrawable(
            ContextCompat.getDrawable(
                binding.root.context,
                if (movie.isFavorite) R.drawable.ic_favorite else R.drawable.ic_favorite_border
            )
        )
    }

    class TransitionListener(
        private val binding: ItemMovieBinding,
        private val movie: Movie
    ) : MotionLayout.TransitionListener {

        private var isExpanded = false

        override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {
            isExpanded = !isExpanded
            binding.imageViewMoviePoster.scaleType = if (isExpanded) ImageView.ScaleType.CENTER_CROP
            else ImageView.ScaleType.CENTER_CROP
            binding.imageViewMoviePoster.load(movie.multimedia?.src)
        }

        override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {}

        override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {}

        override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {}
    }

    companion object {
        fun inflate(parent: ViewGroup?) = MovieViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent?.context),
                R.layout.item_movie,
                parent,
                false
            )
        )
    }
}
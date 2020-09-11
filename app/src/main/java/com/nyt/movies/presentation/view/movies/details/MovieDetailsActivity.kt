package com.nyt.movies.presentation.view.movies.details

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.nyt.movies.R
import com.nyt.movies.databinding.ActivityConverterBinding
import com.nyt.movies.domain.entity.movie.Link
import com.nyt.movies.domain.entity.movie.Movie
import com.nyt.movies.presentation.util.base.BaseActivity
import com.nyt.movies.presentation.util.base.BaseViewModel
import com.nyt.movies.presentation.util.extension.setSafeClickListener
import com.nyt.movies.presentation.view.movies.list.ListMoviesActivity
import com.nyt.movies.presentation.view.movies.list.ListMoviesActivity.Companion.CURRENCY_EXTRA
import org.koin.android.viewmodel.ext.android.viewModel

class MovieDetailsActivity : BaseActivity() {

    override val baseViewModel: BaseViewModel get() = _viewModel
    private val _viewModel: MovieDetailsViewModel by viewModel()

    private lateinit var binding: ActivityConverterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_converter)
        setupUi()
    }

    override fun subscribeUi() {
        super.subscribeUi()
        _viewModel.placeholder.observe(this) { binding.placeholderView.setPlaceholder(it) }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                ORIGIN_CURRENCY_CODE -> {
                    val originCurrency = data?.getSerializableExtra(CURRENCY_EXTRA) as? Movie
                }
                DESTINATION_CURRENCY_CODE -> {
                    val destinationCurrency =
                        data?.getSerializableExtra(CURRENCY_EXTRA) as? Movie
                }
            }
        }
    }

    private fun setupUi() {
        with(binding) {
            originCoinChooser.root.setSafeClickListener { chooseCurrency(ORIGIN_CURRENCY_CODE) }
            destinationCoinChooser.root.setSafeClickListener {
                chooseCurrency(
                    DESTINATION_CURRENCY_CODE
                )
            }
            buttonConvert.setSafeClickListener { }
        }
    }

    private fun chooseCurrency(resultCode: Int) {
        startActivityForResult(
            ListMoviesActivity.createIntent(this),
            resultCode
        )
    }

    private fun onConversionReceived(link: Link?) {
        link?.let {
            with(binding) {}
        }
    }

    companion object {
        const val ORIGIN_CURRENCY_CODE = 123
        const val DESTINATION_CURRENCY_CODE = 321

        fun createIntent(context: Context) =
            Intent(context, MovieDetailsActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
    }
}
package com.nyt.movies.presentation.view.movies.details

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.nyt.movies.R
import com.nyt.movies.databinding.ActivityConverterBinding
import com.nyt.movies.domain.entity.currency.Conversion
import com.nyt.movies.domain.entity.currency.Currency
import com.nyt.movies.presentation.util.base.BaseActivity
import com.nyt.movies.presentation.util.base.BaseViewModel
import com.nyt.movies.presentation.util.constants.TWO_DECIMAL_NUMBER
import com.nyt.movies.presentation.util.extension.observe
import com.nyt.movies.presentation.util.extension.onTextChanges
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
                    val originCurrency = data?.getSerializableExtra(CURRENCY_EXTRA) as? Currency
                    binding.originCurrencyText = originCurrency?.getFormattedString(this)
                }
                DESTINATION_CURRENCY_CODE -> {
                    val destinationCurrency =
                        data?.getSerializableExtra(CURRENCY_EXTRA) as? Currency
                    binding.destinationCurrencyText = destinationCurrency?.getFormattedString(this)
                }
            }
        }
    }

    private fun setupUi() {
        with(binding) {
            originCoinChooser.root.setSafeClickListener { chooseCurrency(ORIGIN_CURRENCY_CODE) }
            originCurrencyText = getString(R.string.hint_origin_currency)
            destinationCoinChooser.root.setSafeClickListener {
                chooseCurrency(
                    DESTINATION_CURRENCY_CODE
                )
            }
            destinationCurrencyText = getString(R.string.hint_destination_currency)
            buttonConvert.setSafeClickListener { }
        }
    }

    private fun chooseCurrency(resultCode: Int) {
        startActivityForResult(
            ListMoviesActivity.createIntent(this),
            resultCode
        )
    }

    private fun onConversionReceived(conversion: Conversion?) {
        conversion?.let {
            with(binding) {
                textViewConvertedValue.text = String.format(TWO_DECIMAL_NUMBER, it.convertedValue)
                textViewOriginCurrency.text = it.originCurrency?.name ?: ""
                textViewDestinationCurrency.text = it.destinationCurrency?.name ?: ""
            }
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
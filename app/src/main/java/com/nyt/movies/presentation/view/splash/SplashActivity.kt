package com.nyt.movies.presentation.view.splash

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.nyt.movies.R
import com.nyt.movies.databinding.ActivitySplashBinding
import com.nyt.movies.presentation.util.base.BaseActivity
import com.nyt.movies.presentation.util.base.BaseViewModel
import com.nyt.movies.presentation.util.extension.transparentStatusAndNavigation
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashActivity : BaseActivity() {

    override val baseViewModel: BaseViewModel get() = _viewModel
    private val _viewModel: SplashViewModel by viewModel()

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        transparentStatusAndNavigation(window)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
    }
}
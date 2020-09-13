package com.nyt.movies.presentation.util.base

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nyt.movies.presentation.util.dialog.DialogData
import com.nyt.movies.presentation.util.extension.showDialog
import com.nyt.movies.presentation.util.navigation.NavData

abstract class BaseActivity : AppCompatActivity() {

    private var dialog: Dialog? = null

    abstract val baseViewModel: BaseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        subscribeUi()
    }

    open fun subscribeUi() {
        baseViewModel.dialog.observe(this, ::onNextDialog)
        baseViewModel.goTo.observe(this, ::onGoTo)
    }

    private fun onNextDialog(dialogData: DialogData?) {
        dialog?.dismiss()
        dialog = dialogData?.let { showDialog(it) }
    }

    private fun onGoTo(navData: NavData?) {
        navData?.navigate(this)
    }
}

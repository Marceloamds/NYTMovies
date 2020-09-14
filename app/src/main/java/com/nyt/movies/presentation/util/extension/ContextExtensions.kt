package com.nyt.movies.presentation.util.extension

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.webkit.URLUtil
import com.nyt.movies.R
import com.nyt.movies.domain.entity.movie.Movie
import com.nyt.movies.presentation.util.constants.INTENT_TEXT_TYPE
import com.nyt.movies.presentation.util.dialog.DialogData

fun Context.showDialog(dialogData: DialogData): Dialog {
    val builder = AlertDialog.Builder(this)
    builder.setTitle(dialogData.title)
    builder.setMessage(dialogData.message)
    if (dialogData.confirmButtonText == null && dialogData.onConfirm == null) {
        builder.setPositiveButton(dialogData.dismissButtonText, dialogData.onDismiss)
    } else {
        builder.setPositiveButton(
            dialogData.confirmButtonText, dialogData.onConfirm
                ?: dialogData.onDismiss
        )
        if (dialogData.dismissButtonText != null || dialogData.onDismiss != null) {
            builder.setNegativeButton(dialogData.dismissButtonText, dialogData.onDismiss)
        }
    }
    dialogData.onDismiss?.let { builder.setOnCancelListener { it() } }
    builder.setCancelable(dialogData.cancelable ?: true)
    return builder.show()
}

fun AlertDialog.Builder.setPositiveButton(buttonText: String?, onClick: (() -> Unit)?) =
    setPositiveButton(
        buttonText ?: context.getString(R.string.global_ok),
        onClick?.let { { _: DialogInterface, _: Int -> it() } }
    )

fun AlertDialog.Builder.setNegativeButton(buttonText: String?, onClick: (() -> Unit)?) =
    setNegativeButton(
        buttonText ?: context.getString(R.string.global_cancel),
        onClick?.let { { _: DialogInterface, _: Int -> it() } }
    )

fun Context.openBrowser(url: String) {
    val formattedUrl = with(url.trim()) {
        if (URLUtil.isHttpUrl(this) || URLUtil.isHttpsUrl(this)) {
            this
        } else {
            "http://$this"
        }
    }
    val browserIntent = Intent(
        Intent.ACTION_VIEW,
        Uri.parse(formattedUrl)
    ).apply { addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) }
    startActivity(browserIntent)
}

fun Activity.shareMovie(movie: Movie) {
    val shareIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, getString(R.string.share_review_text, movie.link.url))
        type = INTENT_TEXT_TYPE
    }
    startActivity(Intent.createChooser(shareIntent, getString(R.string.send_review_to)))
}

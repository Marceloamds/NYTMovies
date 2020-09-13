package com.nyt.movies.presentation.util.dialog

import android.content.Context
import com.nyt.movies.R

class DialogData(
    val title: String,
    val message: String,
    val confirmButtonText: String? = null,
    val onConfirm: (() -> Unit)? = null,
    val dismissButtonText: String? = null,
    val onDismiss: (() -> Unit)? = null,
    val cancelable: Boolean? = true
) {
    companion object {

        fun confirm(
            title: String,
            message: String,
            onConfirm: () -> Unit,
            confirmButtonText: String? = null,
            cancelable: Boolean? = true
        ): DialogData {
            return DialogData(title, message, confirmButtonText, onConfirm, null, null, cancelable)
        }

        fun error(
            context: Context,
            message: String,
            confirmButtonText: String? = null,
            onConfirm: (() -> Unit)? = null,
            onDismiss: (() -> Unit)? = null,
            cancelable: Boolean? = true
        ): DialogData {
            return DialogData(
                context.getString(R.string.error_title),
                message,
                confirmButtonText,
                onConfirm,
                null,
                onDismiss,
                cancelable
            )
        }
    }
}

package com.nyt.movies.presentation.util.error

import android.content.Context
import androidx.annotation.StringRes
import com.nyt.movies.R
import com.nyt.movies.domain.entity.error.HttpErrorType
import com.nyt.movies.domain.entity.error.RequestException
import com.nyt.movies.presentation.util.dialog.DialogData
import com.nyt.movies.presentation.util.logger.Logger

class ErrorHandler constructor(
    private val context: Context,
    private val logger: Logger
) {

    fun getDialogData(
        throwable: Throwable,
        retryAction: (() -> Unit)
    ): DialogData? {
        val errorString = getErrorString(throwable)
        return errorString?.let {
            DialogData.error(context, it, res(R.string.global_try_again), retryAction)
        }
    }

    private fun getErrorString(throwable: Throwable): String? {
        logger.e(throwable)
        return if (throwable is RequestException) {
            when (throwable.httpErrorType) {
                HttpErrorType.UNAUTHORIZED -> res(R.string.error_unauthorized)
                HttpErrorType.TOO_MANY_REQUESTS -> res(R.string.error_too_many_requests)
                else -> null
            }
        } else null
    }

    private fun res(@StringRes stringId: Int) = context.getString(stringId)
}
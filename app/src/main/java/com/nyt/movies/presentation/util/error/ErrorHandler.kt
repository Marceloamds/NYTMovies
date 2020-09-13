package com.nyt.movies.presentation.util.error

import android.content.Context
import androidx.annotation.StringRes
import com.nyt.movies.R
import com.nyt.movies.domain.entity.error.ErrorData
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
        retryAction: (() -> Unit)?
    ): DialogData {
        val data = getErrorData(throwable, retryAction)
        return data.tryAgainAction?.let {
            DialogData.error(context, data.message, res(R.string.global_try_again), it)
        } ?: DialogData.error(context, data.message, res(R.string.global_ok))
    }

    private fun getErrorData(
        throwable: Throwable,
        tryAgainAction: (() -> Unit)? = null
    ): ErrorData {
        logger.e(throwable)
        return if (throwable is RequestException) {
            handleRequestException(throwable, tryAgainAction)
        } else {
            ErrorData.ServerEndHttpErrorData(res(R.string.error_unknown), tryAgainAction)
        }
    }

    private fun handleRequestException(
        exception: RequestException,
        tryAgainAction: (() -> Unit)? = null
    ): ErrorData {
        return when (exception) {
            is RequestException.TimeoutError ->
                ErrorData.ServerEndHttpErrorData(res(R.string.error_socket_timeout), tryAgainAction)
            is RequestException.NetworkError -> ErrorData.ServerEndHttpErrorData(
                res(R.string.error_network),
                tryAgainAction
            )
            is RequestException.UnexpectedError -> ErrorData.ServerEndHttpErrorData(
                exception.errorMessage ?: res(R.string.error_unknown),
                tryAgainAction
            )
            is RequestException.HttpError -> resolveHttpError(exception, tryAgainAction)
        }
    }

    private fun resolveHttpError(
        exception: RequestException,
        tryAgainAction: (() -> Unit)?
    ): ErrorData {
        return when (exception.httpErrorType) {
            HttpErrorType.NOT_FOUND -> ErrorData.ClientEndHttpErrorData(
                exception.errorMessage ?: res(R.string.error_not_found)
            )
            HttpErrorType.TIMEOUT -> ErrorData.ServerEndHttpErrorData(
                res(R.string.error_socket_timeout),
                tryAgainAction
            )
            HttpErrorType.INTERNAL_SERVER_ERROR -> ErrorData.ServerEndHttpErrorData(
                res(R.string.error_internal_server),
                tryAgainAction
            )
            HttpErrorType.UNEXPECTED_ERROR -> ErrorData.ServerEndHttpErrorData(
                res(R.string.error_unexpected),
                tryAgainAction
            )
            HttpErrorType.BAD_REQUEST -> ErrorData.ClientEndHttpErrorData(res(R.string.error_bad_request))
            HttpErrorType.CONFLICT -> ErrorData.ClientEndHttpErrorData(res(R.string.error_conflict))
            HttpErrorType.FORBIDDEN -> ErrorData.ClientEndHttpErrorData(res(R.string.error_forbidden))
            HttpErrorType.UNAUTHORIZED -> ErrorData.ClientEndHttpErrorData(res(R.string.error_unauthorized))
            HttpErrorType.UN_PROCESSABLE_ENTITY -> ErrorData.ClientEndHttpErrorData(res(R.string.error_unprocessable_entity))
            HttpErrorType.TOO_MANY_REQUESTS -> ErrorData.ClientEndHttpErrorData(res(R.string.error_too_many_requests))
            else -> ErrorData.ServerEndHttpErrorData(
                exception.errorMessage
                    ?: exception.message
                    ?: res(R.string.error_unknown),
                tryAgainAction
            )
        }
    }

    private fun res(@StringRes stringId: Int) = context.getString(stringId)
}
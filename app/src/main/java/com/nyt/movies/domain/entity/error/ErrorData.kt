package com.nyt.movies.domain.entity.error

sealed class ErrorData(val message: String, val tryAgainAction: (() -> Unit)?) {

    class ServerEndHttpErrorData(message: String, tryAgainAction: (() -> Unit)?) :
        ErrorData(message, tryAgainAction)

    class ClientEndHttpErrorData(message: String) : ErrorData(message, null)
}
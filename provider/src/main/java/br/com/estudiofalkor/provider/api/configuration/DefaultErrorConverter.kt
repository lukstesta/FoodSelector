package br.com.estudiofalkor.provider.api.configuration

import br.com.estudiofalkor.infrastructure.errorHandling.*
import java.io.EOFException
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException

open class DefaultErrorConverter : ErrorConverter {

    private fun timeout(exception: Exception) = when (exception) {
        is SocketTimeoutException -> ErrorResponse(Timeout, exception.message, exception)
        else -> null
    }

    private fun notConnected(exception: Exception) = when {
        exception is ConnectException || isIO(exception) ->
            ErrorResponse(
                NotConnected,
                exception.message,
                exception
            )
        else -> null
    }

    private fun isIO(exception: Exception) = exception is IOException && exception !is EOFException

    override fun convert(exception: Exception): ErrorResponse? {
        return timeout(exception)
            ?: notConnected(exception)
            ?: ErrorResponse(
                Unexpected,
                exception.message,
                exception
            )
    }
}
package br.com.estudiofalkor.infrastructure.errorHandling

import br.com.estudiofalkor.infrastructure.network.StatusCode

sealed class ErrorType
class Http(
    val statusCode: StatusCode,
    val errorBody: String?
) : ErrorType()

object Unexpected : ErrorType()
object Timeout : ErrorType()
object NotConnected : ErrorType()
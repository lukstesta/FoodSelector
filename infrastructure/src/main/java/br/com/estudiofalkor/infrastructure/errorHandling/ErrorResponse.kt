package br.com.estudiofalkor.infrastructure.errorHandling

import java.lang.RuntimeException

open class ErrorResponse(
    val type: ErrorType,
    override val message: String? = null,
    override val cause: Throwable? = null
) : RuntimeException()

class NullBodyException : RuntimeException()
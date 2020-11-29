package br.com.estudiofalkor.infrastructure.errorHandling

interface ErrorConverter {
    fun convert(exception: Exception): ErrorResponse?
}
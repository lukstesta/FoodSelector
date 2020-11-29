package br.com.estudiofalkor.infrastructure.network

class StatusCodeInvalid(cause: Throwable): RuntimeException("Invalid HTTP status code", cause)
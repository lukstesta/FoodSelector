package br.com.estudiofalkor.infrastructure.errorHandling

sealed class ErrorEvent
object SessionExpiredEvent : ErrorEvent()
object NotConnectedEvent : ErrorEvent()

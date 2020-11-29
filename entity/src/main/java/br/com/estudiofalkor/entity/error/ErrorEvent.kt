package br.com.estudiofalkor.entity.error

sealed class ErrorEvent
object NotConnectedEvent : ErrorEvent()
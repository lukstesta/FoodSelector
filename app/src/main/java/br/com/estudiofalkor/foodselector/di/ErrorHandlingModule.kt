package br.com.estudiofalkor.foodselector.di

import br.com.estudiofalkor.foodselector.errorHandling.ErrorHandlingEventManager
import org.koin.dsl.module

val errorHandlingModule = module {
    single { ErrorHandlingEventManager(get()) }
}
package br.com.estudiofalkor.foodselector.di

import br.com.estudiofalkor.foodselector.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
}
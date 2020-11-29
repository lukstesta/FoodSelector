package br.com.estudiofalkor.foodselector.extension

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import br.com.estudiofalkor.foodselector.errorHandling.DefaultActivityLifecycleObserver
import org.koin.androidx.viewmodel.ext.android.getViewModel

fun AppCompatActivity.setupDefaultLifecycleObserver(
    factory: DefaultActivityLifecycleObserver.Factory
): DefaultActivityLifecycleObserver {
    val observer = factory.create(this)

    lifecycle.addObserver(observer)

    return observer
}

inline fun <reified T : ViewModel> AppCompatActivity.setupViewModel(): T =
    getViewModel()

inline fun <reified T : ViewModel> AppCompatActivity.getViewModel(body: T.() -> Unit): T =
    setupViewModel<T>().apply(body)
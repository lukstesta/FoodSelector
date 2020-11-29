package br.com.estudiofalkor.foodselector.errorHandling

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import br.com.estudiofalkor.foodselector.util.EventBusLifecycleObserver

class DefaultActivityLifecycleObserver(
    private val errorHandlingEventManager: ErrorHandlingEventManager,
    private val activity: AppCompatActivity
) : EventBusLifecycleObserver(activity) {

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        errorHandlingEventManager.register(activity)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onStop() {
        errorHandlingEventManager.register(activity)
    }

    class Factory(
        private val errorHandlingEventManager: ErrorHandlingEventManager
    ) {
        fun create(activity: AppCompatActivity) =
            DefaultActivityLifecycleObserver(errorHandlingEventManager, activity)
    }

}
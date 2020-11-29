package br.com.estudiofalkor.foodselector.util

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.EventBusException

open class EventBusLifecycleObserver(private val subscriber: Any) : LifecycleObserver {

    private val eventBus = EventBus.getDefault()

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun register() {
        try {
            if (!eventBus.isRegistered(subscriber)) {
                EventBus.getDefault().register(subscriber)
            }
        } catch (error: EventBusException) {
            // Ignora excecão ao tentar registrar classes sem @Subscriber
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun unregister() {
        if (eventBus.isRegistered(subscriber)) {
            eventBus.unregister(subscriber)
        }
    }
}
package br.com.estudiofalkor.foodselector.errorHandling

import androidx.appcompat.app.AppCompatActivity
import br.com.estudiofalkor.entity.error.NotConnectedEvent
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class ErrorHandlingEventManager(eventBus: EventBus) {

    private var activity: AppCompatActivity? = null

    init {
        if (!eventBus.isRegistered(this)) eventBus.register(this)
    }

    fun register(activity: AppCompatActivity) {
        this.activity = activity
    }

    @Subscribe
    fun notConnected(event: NotConnectedEvent) {
        activity?.let {
            // call not connected event
        }
    }
}
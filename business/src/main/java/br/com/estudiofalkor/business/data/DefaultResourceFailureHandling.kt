package br.com.estudiofalkor.business.data

import br.com.estudiofalkor.infrastructure.errorHandling.NotConnected
import br.com.estudiofalkor.infrastructure.errorHandling.NotConnectedEvent
import org.greenrobot.eventbus.EventBus

class DefaultResourceFailureHandling(private val eventBus: EventBus) : ResourceFailureHandling {

    override fun handle(failure: Failure) {
        when (failure.error.type) {
            NotConnected -> eventBus.post(NotConnectedEvent)
        }
    }
}

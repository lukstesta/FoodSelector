package br.com.estudiofalkor.business.data

import br.com.estudiofalkor.infrastructure.errorHandling.NotConnected
import br.com.estudiofalkor.infrastructure.errorHandling.NotConnectedEvent
import br.com.estudiofalkor.infrastructure.errorHandling.ErrorResponse
import br.com.estudiofalkor.infrastructure.errorHandling.Unexpected
import br.com.estudiofalkor.testing.any
import br.com.estudiofalkor.testing.capture
import org.greenrobot.eventbus.EventBus
import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito.never
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DefaultResourceFailureHandlingTest {

    @Captor
    private lateinit var captor: ArgumentCaptor<NotConnectedEvent>

    @Mock
    private lateinit var eventBus: EventBus

    private val notConnected = Failure(ErrorResponse(NotConnected))

    private val unexpected = Failure(ErrorResponse(Unexpected))

    @Test
    fun constructor_shouldWorkWithCustomEventBus() {
        try {
            DefaultResourceFailureHandling(eventBus)
        } catch (e: Exception) {
            fail("DefaultResourceFailureHandling wasn't created")
        }
    }

    @Test
    fun handle_shouldPostNotConnectEvent() {
        DefaultResourceFailureHandling(eventBus).handle(notConnected)

        verify(eventBus).post(capture(captor))

        assertTrue(captor.value is NotConnectedEvent)
    }

    @Test
    fun handle_shouldNotPostAnEvent() {
        DefaultResourceFailureHandling(eventBus).handle(unexpected)

        verify(eventBus, never()).post(any())
    }
}
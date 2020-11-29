package br.com.estudiofalkor.foodselector.errorHandling

import androidx.appcompat.app.AppCompatActivity
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DefaultActivityLifecycleObserverTest {

    @Mock
    private lateinit var errorHandlingEventManager: ErrorHandlingEventManager

    @Mock
    private lateinit var activity: AppCompatActivity

    @Test
    fun onStart_shouldWork() {
        DefaultActivityLifecycleObserver(errorHandlingEventManager, activity).onStart()

        Mockito.verify(errorHandlingEventManager).register(activity)
    }

}
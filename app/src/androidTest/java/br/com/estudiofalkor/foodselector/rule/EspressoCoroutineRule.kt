package br.com.estudiofalkor.foodselector.rule

import androidx.test.espresso.IdlingRegistry
import br.com.estudiofalkor.foodselector.idling.EspressoCountingIdlingResource
import br.com.estudiofalkor.infrastructure.coroutines.CoroutineExecutor
import br.com.estudiofalkor.infrastructure.coroutines.toCoroutineContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class EspressoCoroutineRule : TestWatcher() {

    override fun starting(description: Description?) {
        super.starting(description)

        IdlingRegistry.getInstance().register(EspressoCountingIdlingResource)

        CoroutineExecutor.instance.launchExecutor = { asyncContext, block ->
            EspressoCountingIdlingResource.increment()

            CoroutineScope(asyncContext.toCoroutineContext()).launch(block = block)
                .invokeOnCompletion {
                    EspressoCountingIdlingResource.decrement()
                }
        }

        CoroutineExecutor.instance.asyncExecutor = { asyncContext, block ->
            EspressoCountingIdlingResource.increment()

            CoroutineScope(asyncContext.toCoroutineContext()).async(block = block).apply {
                invokeOnCompletion {
                    EspressoCountingIdlingResource.decrement()
                }
            }
        }
    }

    override fun finished(description: Description?) {
        super.finished(description)
        CoroutineExecutor.instance.launchExecutor = null
        CoroutineExecutor.instance.asyncExecutor = null
        IdlingRegistry.getInstance().unregister(EspressoCountingIdlingResource)
    }
}
package br.com.estudiofalkor.foodselector.idling

import android.util.Log
import androidx.test.espresso.IdlingResource
import java.util.concurrent.atomic.AtomicInteger

object EspressoCountingIdlingResource : IdlingResource {

    private val counter = AtomicInteger(0)

    @Volatile
    private var resourceCallback: IdlingResource.ResourceCallback? = null

    override fun getName() = EspressoCountingIdlingResource::class.java.simpleName

    override fun isIdleNow() = counter.get() == 0

    override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback?) {
        resourceCallback = callback
    }

    fun increment() {
        Log.d(name, "EspressoCountingIdlingResource increment")
        counter.getAndIncrement()
    }

    fun decrement() {
        with(counter.decrementAndGet()) {
            if (this == 0) {
                resourceCallback?.onTransitionToIdle()
            }

            if (this < 0) {
                throw IllegalArgumentException("The counter was corrupted")
            }
        }
        Log.d(name, "EspressoCountingIdlingResource decrement")
    }
}
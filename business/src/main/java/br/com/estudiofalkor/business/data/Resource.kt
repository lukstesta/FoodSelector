package br.com.estudiofalkor.business.data

import br.com.estudiofalkor.infrastructure.errorHandling.ErrorResponse
import org.greenrobot.eventbus.EventBus

sealed class Resource<out T>
data class Success<out T>(val data: T) : Resource<T>()
data class Failure(val error: ErrorResponse) : Resource<Nothing>()

fun <T> Resource<T>.handle(body: ResourceContext<T>.() -> Unit) {
    val handler = DefaultResourceFailureHandling(EventBus.getDefault())

    val context = ResourceContext(this, handler)

    body(context)
    context.execute()
}

inline fun <T> resource(body: () -> T): Resource<T> = try {
    Success(body())
} catch (e: ErrorResponse) {
    Failure(e)
}
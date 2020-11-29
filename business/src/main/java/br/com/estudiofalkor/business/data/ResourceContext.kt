package br.com.estudiofalkor.business.data

typealias AboutErrorHandling = () -> Unit

class ResourceContext<T>(
    private val resource: Resource<T>,
    private val defaultFailureHandling: ResourceFailureHandling
) {

    private val customHandleFailures =
        mutableListOf<Failure.(AboutErrorHandling) -> Unit>()

    private var success: (T.() -> Unit)? = null
    private var finally: (() -> Unit)? = null

    fun success(handleSucces: T.() -> Unit) {
        success = handleSucces
    }

    fun failure(handleFailure: Failure.(AboutErrorHandling) -> Unit) {
        customHandleFailures.add(handleFailure)
    }

    fun finally(handleFinally: () -> Unit) {
        finally = handleFinally
    }

    fun execute() {
        when (resource) {
            is Success -> success?.let {
                it(resource.data)
            }
            is Failure -> {
                val handled = customHandleFailures.firstOrNull {
                    var abort = false
                    it(resource) { abort = true }
                    abort
                }

                if (handled == null) {
                    defaultFailureHandling.handle(resource)
                }
            }
        }

        finally?.invoke()
    }
}

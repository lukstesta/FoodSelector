package br.com.estudiofalkor.business.data

import br.com.estudiofalkor.infrastructure.errorHandling.ErrorResponse
import br.com.estudiofalkor.infrastructure.errorHandling.Unexpected
import org.junit.Assert.*
import org.junit.Test
import java.lang.RuntimeException

class ResourceTest {

    @Test
    fun resource_ShouldReturnSuccess() {
        val result = resource { true }

        assertTrue(result is Success<Boolean>)
    }

    @Test
    fun resource_ShouldReturnFailure() {
        val result = resource { throw ErrorResponse(Unexpected) }

        assertEquals(Unexpected, (result as Failure).error.type)
    }

    @Test(expected = RuntimeException::class)
    fun resource_ShouldThrowException() {
        resource { throw RuntimeException() }
    }

    @Test
    fun handle_ShouldWorkWithSuccessResource() {
        val resource = resource { true }

        var work = false
        resource.handle {
            success { work = this }
            failure { fail("Should not pass here") }
        }

        assertTrue(work)
    }

    @Test
    fun handle_ShouldWorkWithFailureResource() {
        val resource = resource { throw ErrorResponse(Unexpected) }

        var work = false

        resource.handle {
            success { fail("Should not pass here") }
            failure { abort ->
                work = true
                abort()
            }
        }

        assertTrue(work)
    }
}
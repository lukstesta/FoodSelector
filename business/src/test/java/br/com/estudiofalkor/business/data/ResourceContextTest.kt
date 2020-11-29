package br.com.estudiofalkor.business.data

import br.com.estudiofalkor.infrastructure.errorHandling.ErrorResponse
import br.com.estudiofalkor.infrastructure.errorHandling.Unexpected
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ResourceContextTest {

    private val success = Success(true)

    private val failure = Failure(ErrorResponse(Unexpected))

    @Mock
    private lateinit var failureHandling: ResourceFailureHandling

    @Test
    fun execute_shouldPerformWithSuccessBlock() {

        val context = ResourceContext(success, failureHandling)

        var work = false

        context.success {
            work = true
        }

        context.execute()

        assertTrue(work)
    }

    @Test
    fun execute_shouldPerformWithFailureBlock() {

        val context = ResourceContext(failure, failureHandling)

        var work = false

        context.failure { abort ->
            work = true
            abort()
        }

        context.execute()

        assertTrue(work)
    }


    @Test
    fun execute_shouldPerformOnlySuccessBlock() {

        val context = ResourceContext(success, failureHandling)

        var work = false

        context.failure { abort ->
            work = false
            abort()
        }

        context.success {
            work = true
        }

        context.execute()

        assertTrue(work)
    }

    @Test
    fun execute_shouldPerformOnlyFailureBlock() {

        val context = ResourceContext(failure, failureHandling)

        var work = false

        context.failure { abort ->
            work = true
            abort()
        }

        context.success {
            work = false
        }

        context.execute()

        assertTrue(work)
    }

    @Test
    fun execute_shouldPerformAllFailureBlocksInOrder() {

        val context = ResourceContext(failure, failureHandling)

        var work = ""

        context.failure {
            work += "A"
        }

        context.failure {
            work += "B"
        }

        context.failure {
            work += "C"
        }

        context.execute()

        assertTrue(work == "ABC")
    }

    @Test
    fun execute_shouldPerformUntilSecondFailureBlock() {

        val context = ResourceContext(failure, failureHandling)

        var work = ""

        context.failure {
            work += "A"
        }

        context.failure { abort ->
            work += "B"
            abort()
        }

        context.failure {
            work += "C"
        }

        context.execute()

        assertTrue(work == "AB")
    }

    @Test
    fun execute_shouldPerformTheDefaultFailureHandlingWithoutAnyBlock() {
        val context = ResourceContext(failure, failureHandling)

        context.execute()

        Mockito.verify(failureHandling).handle(failure)
    }

    @Test
    fun execute_shouldPerformTheDefaultFailureHandlingWithTheSuccessBlock() {
        val context = ResourceContext(failure, failureHandling)

        context.success { }

        context.execute()

        Mockito.verify(failureHandling).handle(failure)
    }

    @Test
    fun execute_shouldPerformTheDefaultFailureHandlingWithTheFailureBlock() {
        val context = ResourceContext(failure, failureHandling)

        context.failure { }

        context.execute()

        Mockito.verify(failureHandling).handle(failure)
    }

    @Test
    fun execute_shouldPerformTheDefaultFailureHandlingWithTheSuccessAndFailureBlocks() {
        val context = ResourceContext(failure, failureHandling)

        context.success { }

        context.failure { }

        context.execute()

        Mockito.verify(failureHandling).handle(failure)
    }

    @Test
    fun execute_shouldPerformTheDefaultFailureHandlingAfterAllFailureBlocks() {

        var result = ""
        var work = ""

        val context = ResourceContext(failure, object : ResourceFailureHandling {
            override fun handle(failure: Failure) {
                result = work
            }
        })

        context.failure {
            work += "A"
        }

        context.failure {
            work += "B"
        }

        context.failure {
            work += "C"
        }

        context.execute()

        assertTrue(result == "ABC")
    }

    @Test
    fun execute_shouldNotPerformTheDefaultFailureHandling() {
        val context = ResourceContext(failure, failureHandling)

        context.failure { abort -> abort() }

        context.execute()

        Mockito.verify(failureHandling, Mockito.times(0)).handle(failure)
    }

    @Test
    fun execute_shouldPerformFinallyBlockWithSuccessResource() {
        val context = ResourceContext(success, failureHandling)

        var work = false

        context.finally { work = true }

        context.execute()

        assertTrue(work)
    }

    @Test
    fun execute_shouldPerformFinallyBlockWithSuccessResourceAndSuccessBlock() {
        val context = ResourceContext(success, failureHandling)

        context.success { }

        var work = false

        context.finally { work = true }

        context.execute()

        assertTrue(work)
    }

    @Test
    fun execute_shouldPerformFinallyBlockWithSuccessResourceAndFailureBlockReturningFalse() {
        val context = ResourceContext(success, failureHandling)

        context.failure { }

        var work = false

        context.finally { work = true }

        context.execute()

        assertTrue(work)
    }

    @Test
    fun execute_shouldPerformFinallyBlockWithSuccessResourceAndFailureBlockReturningTrue() {
        val context = ResourceContext(success, failureHandling)

        context.failure { abort -> abort() }

        var work = false

        context.finally { work = true }

        context.execute()

        assertTrue(work)
    }

    @Test
    fun execute_shouldPerformFinallyBlockWithSuccessResourceAndSuccessAndFailureBlockReturningFalse() {
        val context = ResourceContext(success, failureHandling)

        context.success { }

        context.failure { }

        var work = false

        context.finally { work = true }

        context.execute()

        assertTrue(work)
    }

    @Test
    fun execute_shouldPerformFinallyBlockWithSuccessResourceAndSuccessAndFailureBlockReturningTrue() {
        val context = ResourceContext(success, failureHandling)

        context.success { }

        context.failure { abort -> abort() }

        var work = false

        context.finally { work = true }

        context.execute()

        assertTrue(work)
    }

    @Test
    fun execute_shouldPerformFinallyBlockWithFailureResource() {
        val context = ResourceContext(failure, failureHandling)

        var work = false

        context.finally { work = true }

        context.execute()

        assertTrue(work)
    }

    @Test
    fun execute_shouldPerformFinallyBlockWithFailureResourceAndSuccessBlock() {
        val context = ResourceContext(failure, failureHandling)

        context.success { }

        var work = false

        context.finally { work = true }

        context.execute()

        assertTrue(work)
    }

    @Test
    fun execute_shouldPerformFinallyBlockWithFailureResourceAndFailureBlockReturningFalse() {
        val context = ResourceContext(failure, failureHandling)

        context.failure { }

        var work = false

        context.finally { work = true }

        context.execute()

        assertTrue(work)
    }

    @Test
    fun execute_shouldPerformFinallyBlockWithFailureResourceAndFailureBlockReturningTrue() {
        val context = ResourceContext(failure, failureHandling)

        context.failure { abort -> abort() }

        var work = false

        context.finally { work = true }

        context.execute()

        assertTrue(work)
    }

    @Test
    fun execute_shouldPerformFinallyBlockWithFailureResourceAndSuccessAndFailureBlockReturningFalse() {
        val context = ResourceContext(failure, failureHandling)

        context.success { }

        context.failure { }

        var work = false

        context.finally { work = true }

        context.execute()

        assertTrue(work)
    }

    @Test
    fun execute_shouldPerformFinallyBlockWithFailureResourceAndSuccessAndFailureBlockReturningTrue() {
        val context = ResourceContext(failure, failureHandling)

        context.success { }

        context.failure { abort -> abort() }

        var work = false

        context.finally { work = true }

        context.execute()

        assertTrue(work)
    }
}
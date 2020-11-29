package br.com.estudiofalkor.infrastructure.extensions

import org.junit.Test

class AnyExtensionsTest {

    @Test
    fun cast_toCorrectClass_shouldNotThrowAnException() {
        val value = "Test"
        value.cast<String>()
    }

    @Test(expected = ClassCastException::class)
    fun cast_toIncorrectClass_shouldThrowCastingException() {
        val value = 100
        value.cast<String>()
    }
}
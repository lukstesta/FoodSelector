package br.com.estudiofalkor.foodselector

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import br.com.estudiofalkor.foodselector.rule.EspressoCoroutineRule
import org.junit.Rule
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BaseTest {

    @Rule
    @JvmField
    val coroutineRule = EspressoCoroutineRule()

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

}
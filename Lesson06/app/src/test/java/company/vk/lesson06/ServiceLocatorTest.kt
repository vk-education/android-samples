package company.vk.lesson06

import android.app.Application
import company.vk.lesson06.businesslayer.FactorialCalculator
import company.vk.lesson06.businesslayer.ICalculator
import org.junit.After
import org.junit.Before
import org.junit.Test

class ServiceLocatorTest {
    @Before
    fun setup() {

    }

    @After
    fun shutdown() {

    }


    @Test
    fun check_inject_success() {
        val factory = SimpleFactory()
        ServiceLocator.register(factory)

        val result = ServiceLocator.inject<ICalculator>()
        check(result is FactorialCalculator) { "Expects FactorialCalculator" }

        ServiceLocator.unregister(factory)
    }

    @Test
    fun check_inject_fail() {
        val  result = try {
            ServiceLocator.inject<FactorialCalculator>()
        } catch (e: IllegalStateException) {
            null
        }
        check(result == null){ "Expects null"}
    }
}
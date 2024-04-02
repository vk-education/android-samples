package company.vk.lesson06

import company.vk.lesson06.businesslayer.ICalculator
import org.junit.After
import org.junit.AfterClass
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test

class FactorialCalculatorTest {
    @Before
    fun setup() {

    }

    @After
    fun shutdown() {

    }

    @Test
    fun check_zero() {
        val calculator = ServiceLocator.inject<ICalculator>()

        val value = "0"
        val expectation = "1"
        val result = calculator.calculate(value)

        check(result == expectation) {"Expects '$expectation'"}
    }

    @Test
    fun check_blank() {
        val calculator = ServiceLocator.inject<ICalculator>()

        val value = ""
        val expectation = "Blank"
        val result = calculator.calculate(value)

        check(result == expectation) {"Expects '$expectation'"}
    }


    companion object {
        @BeforeClass
        @JvmStatic
        fun initialSetup() {
            ServiceLocator.register(SimpleFactory())
        }


        @AfterClass
        @JvmStatic
        fun lastShutdown() {

        }
    }
}
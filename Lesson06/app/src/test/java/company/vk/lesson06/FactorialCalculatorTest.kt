package company.vk.Lesson06

import company.vk.Lesson06.businesslayer.ICalculator
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
	fun check_natural() {
		val calculator = ServiceLocator.inject<ICalculator>()

		val value = "10"
		val expectation = "3628800"
		val result = calculator.calculate(value)

		check(result == expectation) { "Expects '$expectation' but received '$result'" }
	}

	@Test
	fun check_unnatural() {
		val calculator = ServiceLocator.inject<ICalculator>()

		val value = "-10"
		val expectation = "Is negative number"
		val result = calculator.calculate(value)

		check(result == expectation) { "Expects '$expectation' but received '$result'" }
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
package company.vk.Lesson06

import company.vk.Lesson06.businesslayer.FactorialCalculator
import company.vk.Lesson06.businesslayer.ICalculator
import org.junit.After
import org.junit.AfterClass
import org.junit.Before
import org.junit.BeforeClass
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

		val calculator = ServiceLocator.inject<ICalculator>()

		check(calculator is FactorialCalculator) { "Expects 'FactorialCalculator' but received '$calculator'" }

		ServiceLocator.unregister(factory)
	}

	@Test
	fun check_inject_fail() {
		val result = try {
			val calculator = ServiceLocator.inject<ICalculator>()
			null
		} catch (error: Throwable) {
			error
		}


		check(result is IllegalStateException) { "Expects 'IllegalStateException' but received '$result'" }
	}
}
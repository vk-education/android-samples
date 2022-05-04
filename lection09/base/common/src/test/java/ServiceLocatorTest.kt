import org.junit.Test
import java.util.concurrent.ConcurrentLinkedDeque

class ServiceLocatorTest {
	@Test
	fun stack_test_add() {
		val stack = ConcurrentLinkedDeque<Int>()

		stack.add(1)
		stack.add(2)
		stack.add(3)


		val builder = StringBuilder()
		stack.forEach {
			builder.append(it)
		}

		val result = builder.toString()

		assert(result == "321") { "incorrect order" }
	}

	@Test
	fun stack_test_push() {
		val stack = ConcurrentLinkedDeque<Int>()

		stack.push(1)
		stack.push(2)
		stack.push(3)


		val builder = StringBuilder()
		stack.forEach {
			builder.append(it)
		}

		val result = builder.toString()

		assert(result == "321") { "incorrect order" }
	}
}
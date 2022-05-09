package company.vk.lection09

import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.junit.Assert
import org.junit.Test
import java.io.ByteArrayOutputStream
import java.io.PrintStream

@ExperimentalCoroutinesApi
class CoroutinesExampleUnitTest {

    private suspend fun originalImplementation(delayInMillis: Long = 1000) {
        val dispatcher = Dispatchers.Default
            .limitedParallelism(1)
        var counter = 0
        GlobalScope.launch {
            repeat(100) {
                launch(dispatcher) {
                    val oldCounter = counter++
                    delay(delayInMillis)
                    println("$oldCounter and ${counter++}")
                }
            }
        }.join()
    }

    private suspend fun fixedImplementation(delayInMillis: Long = 1000) {
        val dispatcher = Dispatchers.Default
            .limitedParallelism(1)

        var counter = 0
        val counterMutex = Mutex()
        GlobalScope.launch {
            repeat(100) {
                launch(dispatcher) {
                    counterMutex.withLock {
                        val oldCounter = counter++
                        delay(delayInMillis)
                        println("$oldCounter and ${counter++}")
                    }
                }
            }
        }.join()
    }

    @Test
    fun originalImplementationTest() {
        val result = doWithMockOutStream {
            runBlocking {
                originalImplementation()
            }
        }
        Assert.assertNotEquals(expectedForWorkImpl(), result)
    }

    @Test
    fun originalImplementationWithAnotherExpectedTest() {
        val result = doWithMockOutStream {
            runBlocking {
                originalImplementation()
            }
        }
        Assert.assertEquals(expectedForWrongImpl(), result)
    }

    @Test
    fun fixedImplementationTest() {
        val result = doWithMockOutStream {
            runBlocking {
                fixedImplementation(delayInMillis = 10)
            }
        }
        Assert.assertEquals(expectedForWorkImpl(), result)
    }

    private fun expectedForWorkImpl(): String {
        val expected = StringBuilder()
        for (i in 0 until 200 step 2) {
            expected.append("$i and ${i + 1}\n")
        }
        return expected.toString()
    }

    private fun expectedForWrongImpl(): String {
        val expected = StringBuilder()
        for (i in 0 until 100) {
            expected.append("$i and ${i + 100}\n")
        }
        return expected.toString()
    }

    private fun doWithMockOutStream(action: () -> Unit): String {
        val consoleStream = System.out
        val byteArrayOutStream = ByteArrayOutputStream()
        System.setOut(PrintStream(byteArrayOutStream))
        action.invoke()
        System.setOut(consoleStream)
        return byteArrayOutStream.toString()
    }
}

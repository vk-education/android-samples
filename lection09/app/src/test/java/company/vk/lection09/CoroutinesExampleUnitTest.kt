package company.vk.lection09

import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.io.ByteArrayOutputStream
import java.io.PrintStream

interface Counter {

    suspend fun increaseCounterAndPrintAsync()
}

@ExperimentalCoroutinesApi
class OriginCounter(
    private val scope: CoroutineScope
) : Counter {

    private var counter = 0

    override suspend fun increaseCounterAndPrintAsync() {
        scope.launch {
            repeat(100) {
                launch {
                    val oldCounter = counter++
                    delay(1000L)
                    println("$oldCounter and ${counter++}")
                }
            }
        }.join()
    }
}

@ExperimentalCoroutinesApi
class MutexCounter(
    private val scope: CoroutineScope
) : Counter {

    private val mutex = Mutex()

    private var counter = 0

    override suspend fun increaseCounterAndPrintAsync() {
        scope.launch {
            repeat(100) {
                launch {
                    mutex.withLock {
                        val oldCounter = counter++
                        delay(1000L)
                        println("$oldCounter and ${counter++}")
                    }
                }
            }
        }.join()
    }
}

@ExperimentalCoroutinesApi
class CoroutinesExampleUnitTest {

    private val printStreamByteArray = ByteArrayOutputStream()
    private val mockPrintStream = PrintStream(printStreamByteArray)
    private val consolePrintStream = System.out

    @Before
    fun before() {
        System.setOut(mockPrintStream)
    }

    @After
    fun after() {
        printStreamByteArray.reset()
        System.setOut(consolePrintStream)
    }

    @Test
    fun originalCounterTest() = runTest {
        OriginCounter(this)
            .increaseCounterAndPrintAsync()
        val result = printStreamByteArray.toString()
        Assert.assertEquals(expectedForOriginalImpl(), result)
    }

    @Test
    fun mutexCounterTest() = runTest {
        MutexCounter(this)
            .increaseCounterAndPrintAsync()
        val result = printStreamByteArray.toString()
        Assert.assertEquals(expectedForMutexImpl(), result)
    }

    private fun expectedForOriginalImpl(): String {
        val expected = StringBuilder()
        for (i in 0 until 100) {
            expected.append("$i and ${i + 100}\n")
        }
        return expected.toString()
    }

    private fun expectedForMutexImpl(): String {
        val expected = StringBuilder()
        for (i in 0 until 200 step 2) {
            expected.append("$i and ${i + 1}\n")
        }
        return expected.toString()
    }
}

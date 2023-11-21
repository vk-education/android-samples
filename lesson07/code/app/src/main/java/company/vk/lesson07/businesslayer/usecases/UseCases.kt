package company.vk.lesson07.businesslayer.usecases

import android.os.SystemClock
import company.vk.lesson07.businesslayer.PlateProviders
import company.vk.lesson07.businesslayer.ProviderFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.delayEach
import kotlinx.coroutines.flow.delayFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart

fun getProviders() = flowOnIO("getProviders") {
    PlateProviders.values()
}

fun putPlate(provider: PlateProviders) = flowOnIO("putPlate($provider)") {
    val color = ProviderFactory.colorProvider().generateColor()
    ProviderFactory.plateProvider(provider).create(color)
}

fun getPlates(provider: PlateProviders) = flowOnIO("getPlates($provider)") {
    ProviderFactory.plateProvider(provider).all()
}

fun clearPlates(provider: PlateProviders) = flowOnIO("clearPlates($provider)") {
    ProviderFactory.plateProvider(provider).clear()
}

private fun <T> flowOnIO(tag: String, block: suspend () -> T) = flow {
    val beginTime = SystemClock.elapsedRealtime()
    val result = block()
    val completeTime = SystemClock.elapsedRealtime()

    println("$tag work time = ${completeTime - beginTime}ms")

    emit(result)
}
    .flowOn(Dispatchers.IO)
    .onStart { delay(1000) }

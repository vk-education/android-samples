package ru.hse.lection05.mvvm.jetpack.modules

import org.koin.dsl.module
import ru.hse.lection05.api.giphy.businesslayer.GiphyProvider
import ru.hse.lection05.base.datalayer.OfflineAccessorFactory
import ru.hse.lection05.base.datalayer.OnlineAccessorFactory

val giphy = module {
    single { OnlineAccessorFactory(get()) }
    single { OfflineAccessorFactory(get()) }
    single { creatGiphyProvider(get(), get()) }
}

fun creatGiphyProvider(onlineFactory: OnlineAccessorFactory, offlineFactory: OfflineAccessorFactory): GiphyProvider {
    val onlineAccessor = onlineFactory.accessor(GiphyProvider.TAG)

    return GiphyProvider(onlineAccessor).apply {
        fastCache = offlineFactory.memoryAccessor(GiphyProvider.TAG)
        slowCache = offlineFactory.persistentAccessor(GiphyProvider.TAG)
    }
}
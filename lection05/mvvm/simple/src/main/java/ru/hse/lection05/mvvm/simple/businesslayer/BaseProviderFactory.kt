package ru.hse.lection05.mvvm.simple.businesslayer

import android.content.Context
import ru.hse.lection05.api.giphy.businesslayer.GiphyProvider
import ru.hse.lection05.base.datalayer.OfflineAccessorFactory
import ru.hse.lection05.base.datalayer.OnlineAccessorFactory

class BaseProviderFactory(context: Context): ProviderFactory.IFactory {
    protected val providers = mutableMapOf<Class<*>, Any?>()
    protected val onlineFactory = OnlineAccessorFactory(context)
    protected val offlineFactory = OfflineAccessorFactory(context)


    override fun <PROVIDER> create(clazz: Class<PROVIDER>): PROVIDER? {
        return when (clazz) {
            GiphyProvider::class.java -> createGiphyProvider() as PROVIDER
            else -> null
        }
    }


    protected fun createGiphyProvider(): GiphyProvider {
        var tmp = providers[GiphyProvider::class.java]
        if (tmp != null) {
            return tmp as GiphyProvider
        }

        val onlineAccessor = onlineFactory.accessor(GiphyProvider.TAG)

        tmp = GiphyProvider(onlineAccessor).apply {
            fastCache = offlineFactory.memoryAccessor(GiphyProvider.TAG)
            slowCache = offlineFactory.persistentAccessor(GiphyProvider.TAG)
        }

        providers[GiphyProvider::class.java] = tmp
        return tmp
    }
}
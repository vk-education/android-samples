package company.vk.lesson07.businesslayer

import android.annotation.SuppressLint
import android.content.Context
import company.vk.lesson07.businesslayer.providers.ColorProvider
import company.vk.lesson07.businesslayer.providers.PlateProvider
import company.vk.lesson07.datalayer.CacheFileAccessor
import company.vk.lesson07.datalayer.ContentProviderAccessor
import company.vk.lesson07.datalayer.DataFileAccessor
import company.vk.lesson07.datalayer.ExternalDataFileAccessor
import company.vk.lesson07.datalayer.MemoryAccessor
import company.vk.lesson07.datalayer.SharedPreferencesAccessor
import company.vk.lesson07.datalayer.SqlAccessor

@SuppressLint("StaticFieldLeak")
object ProviderFactory {
    private lateinit var CONTEXT: Context

    private val PROVIDERS = mutableMapOf<Any, PlateProvider>()

    private val PLATE_PROVIDER_FACTORY = mapOf(
        PlateProviders.MEMORY to { PlateProvider(MemoryAccessor()) },
        PlateProviders.CACHE_DIR to { PlateProvider(CacheFileAccessor(CONTEXT)) },
        PlateProviders.DATA_DIR to { PlateProvider(DataFileAccessor(CONTEXT)) },
        PlateProviders.EXTERNAL_DATA_DIR to { PlateProvider(ExternalDataFileAccessor(CONTEXT)) },
        PlateProviders.SHARED_PREFERENCES to { PlateProvider(SharedPreferencesAccessor(CONTEXT)) },
        PlateProviders.SQLITE to { PlateProvider(SqlAccessor(CONTEXT)) },
        PlateProviders.CONTENT_PROVIDER to { PlateProvider(ContentProviderAccessor(CONTEXT)) }
    )

    private val COLOR_PROVIDER by lazy { ColorProvider() }

    fun plateProvider(provider: PlateProviders): PlateProvider {
        var instance = PROVIDERS[provider]
        if (instance == null) {
            instance = PLATE_PROVIDER_FACTORY[provider]!!()
            PROVIDERS[provider] = instance
        }

        return instance
    }

    fun colorProvider(): ColorProvider {
        return COLOR_PROVIDER
    }

    fun initialize(context: Context) {
        CONTEXT = context.applicationContext
    }
}
package company.vk.lection05.businesslayer

import company.vk.lection05.datalayer.ItemAccessorBridge
import company.vk.lection05.datalayer.LegacyItemAccessor
import company.vk.lection05.datalayer.SimpleItemAccessor
import company.vk.lection05.objects.Strategies

object ItemProviderFactor {
	private val simpleAccessor by lazy { SimpleItemAccessor() }
	private val legacyAccessor by lazy { LegacyItemAccessor("https://cataas.com") }
	private val accessor by lazy { ItemAccessorBridge.create("https://cataas.com") }

	private val providers = mapOf(
		Strategies.SIMPLE_THREAD to { ThreadItemProvider(simpleAccessor) },
		Strategies.SIMPLE_COROUTINE to { CoroutineItemProvider(simpleAccessor) },
		Strategies.SIMPLE_RX to { RxItemProvider(simpleAccessor) },
		Strategies.SIMPLE_FLOW_APPENDING to { FlowAppendingItemProvider(simpleAccessor) },
		Strategies.SIMPLE_RX_APPENDING to { RxAppendingItemProvider(simpleAccessor) },
		Strategies.LEGACY_NETWORK_COROUTINE to { CoroutineItemProvider(legacyAccessor) },
		Strategies.RETROFIT_NETWORK_COROUTINE to { CoroutineItemProvider(accessor) },
	)


	fun create(strategy: Strategies): AbstractItemProvider {
		return providers.getValue(strategy)()
	}
}
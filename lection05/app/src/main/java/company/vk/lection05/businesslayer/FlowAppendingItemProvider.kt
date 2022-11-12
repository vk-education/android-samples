package company.vk.lection05.businesslayer

import company.vk.lection05.datalayer.IItemAccessor
import company.vk.lection05.datalayer.SimpleItemAccessor
import company.vk.lection05.objects.Item
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

internal class FlowAppendingItemProvider(accessor: IItemAccessor): AbstractItemProvider(accessor) {
	protected val scope = CoroutineScope(Dispatchers.Main)


	override fun load(callback: (List<Item>) -> Unit) {
		val flow = flow {
			val result = accessor.items()
			val response = mutableListOf<Item>()

			result.forEach {
				delay(500)
				response.add(it)
				emit(response.toList())
			}
		}.flowOn(Dispatchers.IO)


		scope.launch {
			flow.collectLatest {
				callback(it)
			}
		}
	}
}
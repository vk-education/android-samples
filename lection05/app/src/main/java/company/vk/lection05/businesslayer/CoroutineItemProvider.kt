package company.vk.lection05.businesslayer

import company.vk.lection05.datalayer.IItemAccessor
import company.vk.lection05.datalayer.SimpleItemAccessor
import company.vk.lection05.objects.Item
import kotlinx.coroutines.*

internal class CoroutineItemProvider(accessor: IItemAccessor): AbstractItemProvider(accessor) {
	protected val scope = CoroutineScope(Dispatchers.Main)

	override fun load(callback: (List<Item>) -> Unit) {
		scope.launch {
			try {
				val result = withContext(Dispatchers.IO) { accessor.items() }
				callback(result)
			} catch (error: Throwable) {
				error.printStackTrace()
			}
		}
	}
}
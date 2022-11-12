package company.vk.lection05.businesslayer

import android.graphics.Color
import company.vk.lection05.datalayer.IItemAccessor
import company.vk.lection05.objects.Item

abstract class AbstractItemProvider(val accessor: IItemAccessor) {
	abstract fun load(callback: (List<Item>) -> Unit)


	companion object {
		const val LIMIT = 100
		val COLOR_MAP = mapOf(
			0 to Color.RED,
			1 to Color.BLUE
		)
	}
}
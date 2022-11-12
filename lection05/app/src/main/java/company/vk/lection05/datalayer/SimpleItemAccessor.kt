package company.vk.lection05.datalayer

import company.vk.lection05.businesslayer.AbstractItemProvider
import company.vk.lection05.objects.Item

internal class SimpleItemAccessor: IItemAccessor {
	override fun items(): List<Item> {
		val result = mutableListOf<Item>()

		for (index in 1 ..AbstractItemProvider.LIMIT) {
			val item = createItem(index)
			result.add(item)
		}

		return result
	}


	protected fun createItem(index: Int): Item {
		val colorIndex = index % 2

		val item = Item().apply {
			id = index.toString()
			color = AbstractItemProvider.COLOR_MAP.getValue(colorIndex)
			value = index.toString()
		}

		return item
	}
}
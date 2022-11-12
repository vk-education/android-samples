package company.vk.lection05.datalayer

import company.vk.lection05.objects.Item

interface IItemAccessor {
	fun items(): List<Item>
}
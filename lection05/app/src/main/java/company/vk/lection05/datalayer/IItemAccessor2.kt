package company.vk.lection05.datalayer

import company.vk.lection05.objects.Item
import retrofit2.http.GET

interface IItemAccessor2 {
	@GET("/api/cats?skip=0&limit=100")
	suspend fun items2(): List<Item>
}
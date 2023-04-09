package company.vk.Lesson06.datalyaer

import company.vk.Lesson06.objects.Item
import company.vk.Lesson06.objects.PagingResult
import retrofit2.http.GET
import retrofit2.http.Query

interface ICharacterAccessor {
	@GET("character")
	suspend fun list(@Query("page") page: Int, @Query("count") count: Int): PagingResult<Item>
}
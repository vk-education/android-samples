package company.vk.lesson06.datalayer

import company.vk.lesson06.objects.Item
import company.vk.lesson06.objects.PagingResult
import retrofit2.http.GET
import retrofit2.http.Query

interface ICharacterAccessor {
    @GET("character")
    suspend fun list(@Query("page") page: Int, @Query("count") count: Int): PagingResult<Item>
}
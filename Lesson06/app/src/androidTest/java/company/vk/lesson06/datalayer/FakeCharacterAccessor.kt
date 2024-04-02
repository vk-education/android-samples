package company.vk.lesson06.datalayer

import company.vk.lesson06.objects.Item
import company.vk.lesson06.objects.PagingResult

open class FakeCharacterAccessor: ICharacterAccessor {
    override suspend fun list(page: Int, count: Int): PagingResult<Item> {
        val start = page * count
        val end = (page + 1) * count

        val fakeList = (start until end).map {
            Item().apply {
                id = (-it).toLong()
                name = "Fake Name $it"
                species = "Fake Species $it"
            }
        }

        val fakePage = PagingResult<Item>().apply {
            results = fakeList
        }

        return fakePage
    }
}
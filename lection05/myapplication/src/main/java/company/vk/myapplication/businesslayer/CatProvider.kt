package company.vk.myapplication.businesslayer

import com.google.gson.Gson
import company.vk.myapplication.datalayer.IAccessor
import company.vk.myapplication.objects.Cat

class CatProvider(val accessor: IAccessor) {
	suspend fun getCats(offset: Int, limit: Int): List<Cat> {
		return accessor.getCats(offset, limit)
	}
}
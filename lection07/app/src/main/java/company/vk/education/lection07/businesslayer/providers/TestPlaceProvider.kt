package company.vk.education.lection07.businesslayer.providers

import company.vk.education.lection07.objects.AbstractPlace

class TestPlaceProvider: IPlaceProvider {
    override fun find(query: String, callback: (result: Result<List<AbstractPlace>>) -> Unit) {
        callback(Result.Fail(null))
    }
}
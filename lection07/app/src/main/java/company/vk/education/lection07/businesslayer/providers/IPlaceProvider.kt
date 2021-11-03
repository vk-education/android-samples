package company.vk.education.lection07.businesslayer.providers

import company.vk.education.lection07.objects.AbstractPlace

interface IPlaceProvider {
    fun find(query: String, callback: (result: Result<List<AbstractPlace>>) -> Unit)
}
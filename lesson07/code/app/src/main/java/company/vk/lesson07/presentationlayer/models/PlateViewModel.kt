package company.vk.lesson07.presentationlayer.models

import androidx.lifecycle.ViewModel
import company.vk.lesson07.businesslayer.PlateProviders
import company.vk.lesson07.businesslayer.usecases.clearPlates
import company.vk.lesson07.businesslayer.usecases.getPlates
import company.vk.lesson07.businesslayer.usecases.putPlate

class PlateViewModel: ViewModel() {
    fun addPlate(provider: PlateProviders) = putPlate(provider)
    fun all(provider: PlateProviders) = getPlates(provider)
    fun clearAll(provider: PlateProviders) = clearPlates(provider)
}

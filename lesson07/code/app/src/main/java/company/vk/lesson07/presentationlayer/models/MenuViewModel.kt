package company.vk.lesson07.presentationlayer.models

import androidx.lifecycle.ViewModel
import company.vk.lesson07.businesslayer.usecases.getProviders

class MenuViewModel: ViewModel() {
    fun providers() = getProviders()
}

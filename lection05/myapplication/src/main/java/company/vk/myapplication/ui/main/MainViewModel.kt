package company.vk.myapplication.ui.main

import androidx.lifecycle.ViewModel
import company.vk.myapplication.ServiceLocator

class MainViewModel : ViewModel() {
	private val provider = ServiceLocator.provider()

	suspend fun getCats() = provider.getCats(0, 100)
}
package company.vk.base.videos.presentationlayer

import androidx.lifecycle.ViewModel
import company.vk.base.videos.businesslayer.FilmManager
import company.vk.base.films.objects.Film
import company.vk.common.ServiceLocator.inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FilmViewModel: ViewModel() {
	protected val manager by inject<FilmManager>()


	protected val filmList = MutableStateFlow<States<List<Film>>>(States.None())


	fun filmList(): StateFlow<States<List<Film>>> = filmList

	fun load() {
		manager.filmList { result, error ->
			when {
				result == null -> filmList.value = States.Fail(error)
				else -> filmList.value = States.Success(result)
			}
		}
	}
}
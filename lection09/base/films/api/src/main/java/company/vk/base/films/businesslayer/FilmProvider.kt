package company.vk.base.films.businesslayer

import company.vk.base.films.datalayer.IApiAccessor
import company.vk.base.films.objects.Film
import company.vk.common.ServiceLocator.inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FilmProvider: IFilmProvider {
	protected val api by inject<IApiAccessor>()

	protected val scope = CoroutineScope(Dispatchers.IO)


	override fun filmList(callback: (result: List<Film>?, error: Throwable?) -> Unit) {
		scope.launch {
			try {
				callback.returnResult(api.filmList() ,null)
			} catch (error: Throwable) {
				callback.returnResult(null ,error)
			}
		}
	}
}

private suspend fun <R> ((result: R?, error: Throwable?) -> Unit).returnResult(result: R?, error: Throwable?) {
	withContext(Dispatchers.Main) {
		this@returnResult.invoke(result, error)
	}
}

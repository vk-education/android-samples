package company.vk.base.videos.presentationlayer

sealed class States<T> {
	class None<T>(): States<T>()
	class Pending<T>(): States<T>()
	class Success<T>(val result: T): States<T>()
	class Fail<T>(val error: Throwable?): States<T>()
}
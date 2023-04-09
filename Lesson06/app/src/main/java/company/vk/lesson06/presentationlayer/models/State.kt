package company.vk.Lesson06.presentationlayer.models

sealed class State<T>()
class Pending<T>: State<T>()
data class Success<T>(val data: T?): State<T>()
data class Fail<T>(val error: Throwable?): State<T>()
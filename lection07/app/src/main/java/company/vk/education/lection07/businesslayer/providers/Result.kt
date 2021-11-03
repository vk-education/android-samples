package company.vk.education.lection07.businesslayer.providers

sealed interface Result<TYPE> {
    data class Success<TYPE>(var data: TYPE): Result<TYPE>
    data class Fail<TYPE>(var error: Throwable?): Result<TYPE>
}
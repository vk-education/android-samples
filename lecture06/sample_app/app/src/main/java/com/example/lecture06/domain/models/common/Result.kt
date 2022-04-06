package com.example.lecture06.domain.models.common

sealed interface Result<out Data> {

    data class Success<Data>(val data: Data) : Result<Data>
    data class Error(val throwable: Throwable) : Result<Nothing>
}
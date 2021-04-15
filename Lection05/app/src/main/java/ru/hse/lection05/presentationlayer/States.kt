package ru.hse.lection05.presentationlayer

sealed class States<RESULT>

data class Success<RESULT>(val result: RESULT): States<RESULT>()
data class Fail<RESULT>(val error: Throwable?): States<RESULT>()
data class Pending<RESULT>(val attempt: Int = 1): States<RESULT>()
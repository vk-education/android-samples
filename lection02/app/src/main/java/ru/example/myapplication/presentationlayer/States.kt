package ru.example.myapplication.presentationlayer

sealed class States<DATA>

class None<DATA>(): States<DATA>()
class Pending<DATA>(): States<DATA>()
data class Success<DATA>(val result: DATA): States<DATA>()
data class Fail<DATA>(val error: Throwable?): States<DATA>()

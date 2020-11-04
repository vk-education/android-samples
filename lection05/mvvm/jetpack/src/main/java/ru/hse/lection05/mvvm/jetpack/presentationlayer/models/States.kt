package ru.hse.lection05.mvvm.jetpack.presentationlayer.models

import ru.hse.lection05.api.giphy.objects.Item

sealed class State(val isFinal: Boolean)

class None: State(false)
class Pending: State(false)
class ItemListSuccess(val result: List<Item>): State(true)
class Fail(val error: Throwable?): State(true)
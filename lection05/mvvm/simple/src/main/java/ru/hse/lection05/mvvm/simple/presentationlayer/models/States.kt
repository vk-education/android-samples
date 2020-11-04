package ru.hse.lection05.mvvm.simple.presentationlayer.models

import android.os.SystemClock
import ru.hse.lection05.api.giphy.objects.Item

sealed class State()
class None: State()
class Pending(val tag: Long = SystemClock.elapsedRealtime()): State()
class ItemListSuccess(val result: List<Item>): State()
class Fail(val error: Throwable?): State()
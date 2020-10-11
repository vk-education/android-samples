package ru.hse.lection03.objects

import java.io.Serializable

class Droid(
        val name: String
        , val state: Int
): Serializable {
    companion object {
        const val STATE_REMOVED = 0
        const val STATE_NEW = 1
    }
}
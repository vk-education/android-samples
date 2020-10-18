package ru.hse.lection04.businesslayer

import ru.hse.lection04.R

/**
 * Каналы, которые есть в приложении
 */
enum class Channels(val id: String, val nameResId: Int) {
    CONNECTIVITY("CONNECTIVITY", R.string.app_channel_connectivity);
}
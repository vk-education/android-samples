package ru.ok.technopolis.customview

import java.util.*

object WaveRepository {

    const val MAX_VOLUME = 100
    private const val WAVE_LENGTH = 200

    @JvmStatic
    val waveData: IntArray
        get() {
            val data = IntArray(WAVE_LENGTH)
            val r = Random()
            for (i in data.indices) {
                val value = r.nextInt(MAX_VOLUME + 1)
                data[i] = value
            }
            return data
        }
}
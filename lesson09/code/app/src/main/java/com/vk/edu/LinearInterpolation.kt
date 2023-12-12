package com.vk.edu

// https://github.com/ajbanks/arff-builder/blob/3fc8f6860a37d33cd93081a9c8c0025fd70872f5/src/stretching/LinearInterpolation.java
object LinearInterpolation {

    fun interpolateArray(source: IntArray, destinationLength: Int): IntArray {
        val destination = IntArray(destinationLength)
        destination[0] = source[0]
        var jPrevious = 0
        for (i in 1 until source.size) {
            val j = i * (destination.size - 1) / (source.size - 1)
            interpolate(destination, jPrevious, j, source[i - 1].toDouble(), source[i].toDouble())
            jPrevious = j
        }
        return destination
    }

    private fun interpolate(destination: IntArray, destFrom: Int, destTo: Int, valueFrom: Double, valueTo: Double) {
        val destLength = destTo - destFrom
        val valueLength = valueTo - valueFrom
        for (i in 0..destLength) {
            destination[destFrom + i] = (valueFrom + valueLength * i / destLength).toInt()
        }
    }
}
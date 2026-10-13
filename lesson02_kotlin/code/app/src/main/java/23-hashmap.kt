package com.pa_vel.kotlin

import kotlin.random.Random

fun main() {
    val map = mutableMapOf<BadHashCode, String>()

    val key1 = BadHashCode()
    val key2 = BadHashCode()

    map.put(key1, "value1")
    map.put(key2, "value2")

    println("key1=${map.get(key1)}\nkey2=${map.get(key2)}")

    val key3 = VeryBadHashCode()
    val key4 = VeryBadHashCode()
    val mapVeryBad = mutableMapOf<VeryBadHashCode, String>().also {
        it[key3] = "value3"
        it[key4] = "value4"
    }

    println("key3=${mapVeryBad[key3]}\nkey4=${mapVeryBad[key4]}")
}

class BadHashCode {
    override fun hashCode(): Int {
        return 1
    }
}

class VeryBadHashCode {
    override fun hashCode(): Int {
        return Random.nextInt()
    }
}
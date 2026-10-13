package com.pa_vel.kotlin

// const val I_AM_CONST = "I am const"

//object Singleton(val name: String) {
object Singleton {
    const val name = "Singleton"

    fun doSomething() {
        println("I do something")
    }
}

fun main() {
//    val obj = Singleton()
    val obj = Singleton
    obj.name
    Singleton.name

    println("object=${Singleton}\ndata object=${Kotlin_1_9_feature}")

    val withCompanion1 = WithCompanion.newInstance("1")
    val withCompanion2 = WithCompanion.newInstance("2")
    println("Instances: ${WithCompanion.instancesCount}")
}

data object Kotlin_1_9_feature {
    val name = "Data object name"
}

class WithCompanion private constructor(name: String) {
    val myName = name

    init {
        instancesCount++
    }

    companion object {
        var instancesCount = 0
        fun newInstance(name: String) = WithCompanion(name)
    }
}


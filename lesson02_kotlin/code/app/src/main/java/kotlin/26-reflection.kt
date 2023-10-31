package com.pa_vel.kotlin

import kotlin.reflect.full.memberFunctions
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible

class ClassToReflect {
    val timeCreated = System.currentTimeMillis()

    fun callMe(): String {
        return "Thank you for calling"
    }
}


fun main() {
    val obj = ClassToReflect()
    val kClass = obj.javaClass.kotlin
    println(kClass.members.joinToString { it.name })

    val prop = kClass.memberProperties.find { it.name == "timeCreated" }
    val method = kClass.memberFunctions.find { it.name == "callMe" }
    println("Reflected: timeCreated=${prop?.get(obj)} callMe=${method?.call(obj)}")
}


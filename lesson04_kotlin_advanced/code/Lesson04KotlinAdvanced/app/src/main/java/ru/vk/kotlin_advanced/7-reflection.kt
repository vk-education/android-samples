package ru.vk.kotlin_advanced


//kotlin reflect library, see libs.versions.toml file
import kotlin.reflect.full.memberFunctions
import kotlin.reflect.full.memberProperties

class ClassToReflect {
    val timeCreated = System.currentTimeMillis()

    fun callMe(): String {
        return "Thank you for calling"
    }
}


fun main() {
    val classToReflect = ClassToReflect()
    val kClass = classToReflect.javaClass.kotlin
    println(kClass.members.joinToString { it.name })

    val prop = kClass.memberProperties.find { it.name == "timeCreated" }
    val method = kClass.memberFunctions.find { it.name == "callMe" }
    println("Reflected: timeCreated=${prop?.get(classToReflect)} callMe=${method?.call(classToReflect)}")
}


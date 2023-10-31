package com.pa_vel.kotlin

interface Clickable {
    fun click()
}

interface Focusable {
    fun focus()
}

interface Debuggable {
    fun log(): String = this.javaClass.simpleName
}

class View : Clickable, Focusable, Debuggable {
    override fun click() {
        println("Click")
    }

    override fun focus() {
        println("Focus")
    }

//    override fun log(): String {
//        return "overriden log"
//    }
}

fun clicker(clickable: Clickable) {
    clickable.click()
}


fun main() {
    val view = View()
    clicker(view)

    val example = Example()
    debugger(view)
    debugger(example)
}

class Example : Debuggable

fun debugger(debuggable: Debuggable) {
    println(debuggable.log())
}

interface Loggable {
    fun log() = "loggable log"
}

//class Collision : Debuggable, Loggable
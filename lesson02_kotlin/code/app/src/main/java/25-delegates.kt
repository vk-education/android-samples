package com.pa_vel.kotlin

class ListWithGetCounter(
    private val innerList: List<String>
) : List<String> by innerList  {
    var counter = 0
        private set

    override fun get(index: Int): String {
        counter++
        return innerList[index]
    }
}

class WithLazy {
    val className: String by lazy {
        println("lazy called")
        javaClass.simpleName
    }
}

fun main() {
    println(ListWithGetCounter(listOf("1", "2", "3")).counter)

    val withLazy = WithLazy()
    withLazy.className
}
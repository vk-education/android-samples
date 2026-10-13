package com.pa_vel.kotlin

fun main(args: Array<String>) {
    val helloWorld: String = "Hello World!"
    println(helloWorld)



//    helloWorld = "New string"

    var varHelloWorld = "Hello World!"
    println(varHelloWorld)

    varHelloWorld = "Good bye, World!"
    println(varHelloWorld)
//
////    helloWorld = "Good bye, World!"
//    println(helloWorld)
//
    val a: Int = 1

    val arr = arrayListOf("a", "b", "c")
    val arr2 = arr

    arr.add("d")
    println(arr)
//
//    for (i in 0 until 7) {
//        println(i)
//    }

    defaultParams(
        a = "1",
        b = "new-3",
        c = "new-a",
    )
}

fun max(a: Int, b: Int): Int {
    return if (a > b) a else b
}

fun max2(a: Int, b: Int) = if (a > b) a else b

fun paramNames(
    a: String,
    b: String,
    c: String,
) {
    println("a=$a; b=$b; c=$c")
    TODO()
}


fun defaultParams(
    a: String,
    b: String = "2",
    c: String = "3",
) {
    println("a=$a; b=$b; c=$c")
}

fun getName(): String {
  return "Name"
}
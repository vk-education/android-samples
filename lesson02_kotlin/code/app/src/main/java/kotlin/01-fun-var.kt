fun main(args: Array<String>) {
    val helloWorld: String = "Hello World!"
    println(helloWorld)

    var varHelloWorld = "Hello World!"
    println(varHelloWorld)

    varHelloWorld = "Good bye, World!"
    println(varHelloWorld)

//    helloWorld = "Good bye, World!"
    println(helloWorld)

    val arr = arrayListOf("a", "b", "c")
    arr.add("d")
    println(arr)

    for (i in 0 until 7) {
        println(i)
    }

    defaultParams("1", c = "3")
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
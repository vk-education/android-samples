import java.util.Date

//kotlinTypes()
//fun kotlinTypes(): Unit {
//    val a: String? = null
//
//    val unit = println("")
//    println(unit)
//
//    // Any:
//    1.toString()
//    unit.hashCode()
//
////     return Unit
////     return
////     return unit
//}

//givesNothing()
fun givesNothing(): Nothing {
    throw RuntimeException("Always crash")
}

fun main() {
    typeCasting("asd")
}

//nullableTypes("a")
fun nullableTypes(input: String?): String? {
    input ?: return null

    input.length
    println(input is Any)
    println(input is Any?)
    println(input is String)
    println(input is String?)

    return input
}

fun typeCasting(input: Any?) {
    println(input is String)
    println(input as String)

    input.chars() // now it's String
}
//typeCasting(2)

import java.lang.NullPointerException
import java.lang.StringBuilder

//fun main() {
    val b: Char = 'b'
//    val one = 1
//
//    val boolTrue: Boolean = true
//    val boolFalse = false
//
//    println("${boolTrue || boolFalse } " +
//            "${boolTrue && boolFalse} " +
//            "${!boolTrue}")
//
//
//    val number: Int = 1
//    println("${Int.MIN_VALUE} ${Int.MAX_VALUE}")
//
//    val longNumber: Long = 1
//    val longNumber2 = 1L
//    println("${Long.MIN_VALUE} ${Long.MAX_VALUE}")
//
//    val shortNumber: Short = 1
//    val byteNumber: Byte = 1
//    println("Short: ${Short.MIN_VALUE} ${Short.MAX_VALUE}")
//    println("Byte: ${Byte.MIN_VALUE} ${Byte.MAX_VALUE}")
//
//
//    val doubleNumber: Double = 1.0
//    val floatNumber: Float = 1F
////    val floatNumber: Float = 1 // error
////    val doubleNumber: Double = 1 // error
//    println("Double: ${Double.MIN_VALUE} ${Double.MAX_VALUE}")
//    println("Float: ${Float.MIN_VALUE} ${Float.MAX_VALUE}")
//
//    val arr = intArrayOf(1, 2, 3)
//    val arr2 = arrayOf(1, 2, 3)
//    byteArrayOf(1, 2, 3)
//    booleanArrayOf(false, true, true)
//}
////typesDemo()
//
//fun charAndString() {
//    val charA = 'a'
//    val charAExpl: Char = 'a'
//
//    // ---
//
//    val hello = "hello"
//    val world = "world"
//    val one = 1
//    val three = 3
//
//    println("$hello ${world} ${three + one}")
//    println("$hello ${world} $three + one")
//
//
//    val a = "a"
//
//    val a1 = buildString {
//        repeat(1000) {
//            append(a)
//        }
//    }
//
//    println(a1)

//    val json = """
//        {
//            "name": "Ivan",
//            "surname": "Petrov"
//        }
//    """.trimIndent()
//    println(json)
//}
// charAndString()

//typeHierarchy()
fun main() {
    val one: Int = 1
    println(one is Int)
    println(one is Number)
    println(one is Any)


}

//fun retNoth(): Nothing {
//
//}
import java.util.Date

fun main() {
    val a: Any? = Date()

    val str = a.toString()
    val date = a as Date

    forLoop2()
}

fun forLoop() {
    println("Include end")
    for (i in 0..7) {
        println(i)
    }

    println("Exclude end")
    for (i in 0 until 7) {
        println(i)
    }
}
//forLoop()

fun forLoopChar() {
    for (c in 'A'..'z') {
        println(c)
    }
}
//forLoopChar()


fun forLoop2() {


    for (i in 1000..< 0) {
        println(i)
    }
}
//forLoop2()
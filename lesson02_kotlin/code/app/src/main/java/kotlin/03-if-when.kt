fun ifElse(input: Int) {
    if (input > 0) {
        println("> 0")
    } else if (input == 0) {
        println("0")
    } else {
        println("< 0")
    }
}
//ifElse(1)

fun withWhen(input: Int) {
    when {
        input > 0 -> println("> 0")
        input == 0 -> println("0")
        else -> println("< 0")
    }
}

fun fromWhen(input: Int) {
    when (input) {
        -1 -> println("negative one")
        0 -> println("zero")
        1 -> println("one")
        2, 3, 4 -> println("two, three or four")
        else -> println("not supported")
    }
}
//fromWhen(3)

fun recognize(c: Char) = when (c) {
    in '0'..'9' -> "It's a digit!"
    in 'a'..'z', in 'A'..'Z' -> "It's a letter!"
    else -> "I don't know…"
}
//recognize('3')

fun inputSize(input: Int) {
    val str = when {
        input < 0 -> "negative"
        input < 10 -> "small"
        input < 100 -> "medium"
        input < 1000 -> {
            println("used code block here")
            "large"
        }
        else -> "enormous"
    }
    println(str)
}
//inputSize(10000)

fun smartCast(input: Any) {
    when (input) {
        is String -> println("${input.chars()}")
        is Int -> println("${input.div(2)}")
        is List<*> -> println("${input.subList(0, 1)}")
    }
}
//smartCast(8)

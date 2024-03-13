package ru.vk.kotlin_advanced

fun main() {
    val currentExampleNumber = 4

    when (currentExampleNumber) {
        1 -> letExample()
        2 -> alsoExample()
        3 -> applyExample()
        4 -> runExample()
        5 -> withExample()
    }
}


// let
// часто используется для безопасного выполнения блока кода с null-выражениями
fun letExample() {
    val activity: Int? = null

    activity?.let { it * 2 }
    //println("a = $a")
}

//also
//используется для выполнения каких-либо дополнительных действий

fun alsoExample() {
    val numbers = mutableListOf("one", "two", "three")
    numbers
        .also { list -> println("The list elements before adding new one: $list") }
        .add("four")

    println("The list after also: $numbers")


    //Можно поменять местами переменнные без создания промежуточной
    var a = 5
    var b = 10
    println("a = $a, b = $b")

    a = b.also { b = a }

    println("a = $a, b = $b")
}

//apply
//настроить объект и не надо возвращать результат

fun applyExample() {

    val numberList = mutableListOf<Double>()
    numberList.also { println("Populating the list") }
        .apply {
            add(2.71)
            add(3.14)
            add(1.0)
            println(numberList)
        }
        .also { println("Sorting the list") }
        .sort()

    println("Sorted numberList $numberList")
}

//run, with

//run и with очень похожи, поэтому не рекомендуется использовать их вместе.

//run - используется для настройки объекта и вычисления результата

fun runExample() {
    StringBuilder().run {
        for (letter in 'A'..'Z'){
            append(letter)
        }
        println(toString())
    }


}

//with - используется для объединения вызовов функций объекта
fun withExample() {
    val numbers = mutableListOf("one", "two", "three")
    with(numbers) {
        val firstItem = this.first()
        val lastItem = last()
        println("First item: $firstItem, last item: $lastItem")
    }
}
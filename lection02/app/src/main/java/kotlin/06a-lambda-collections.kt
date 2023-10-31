import java.util.Calendar

data class Human2(val name: String, val age: Int, val isWoman: Boolean)

fun demoCollectionsFunctions() {

    val people = listOf(
        Human2("Oleg", 23, isWoman = false),
        Human2("Olga", 21, isWoman = true),
        Human2("Andrey", 20, isWoman = false),
        Human2("Kira", 19, isWoman = true),
    )
    println("Are women in the list? ${if ( people.any { it.isWoman } ) "Yes." else "No."} ")

    val womenWithAgeCapitalized = people.filter {
        it.isWoman
    }.map {
        "${it.name} (${it.age})"
    }.joinToString(prefix = "woman: <", separator = "\n", postfix = ">") {
        it.uppercase()
    }

    println(womenWithAgeCapitalized)
}
// demoCollectionsFunctions()

//flatMapDemo()
fun flatMapDemo() {
    val menList = listOf(
        Human2("Alex", 15, isWoman = false),
        Human2("Jack", 21, isWoman = false),
    )

    val womanList = listOf(
        Human2("Jane", 20, isWoman = true),
        Human2("Sandra", 17, isWoman = true),
    )

    val listOfLists = listOf(menList, womanList)

    println(adults(listOfLists))
}

fun adults(lists: List<List<Human2>>): List<String> {
    return lists.flatMap {
        it.filter { it.age >= 18 }.map { it.name }
    }
}
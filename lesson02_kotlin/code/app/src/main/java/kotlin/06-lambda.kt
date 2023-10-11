fun acceptsLambda(lambda: (Boolean, String) -> Any) {
    lambda(true, "string")
}

fun main() {

    val lambda = { firstParam: Boolean, secondParam: String ->
        println("$firstParam $secondParam")
        firstParam as Any
    }
    acceptsLambda(lambda)
}


/*
button.setOnClickListener(new OnClickListener() {
    @Override
    public void onClick(View view) {
        doWhenClicked();
    }
});
 */

/*
button.setOnClickListener {
    /* do on click */
}
*/

data class Human(val name: String, val age: Int)

fun <T> findFirst(iterable: Iterable<T>, predicate: (T) -> Boolean): T {
    for (element in iterable) if (predicate(element)) return element
    throw NoSuchElementException("Collection contains no element matching the predicate.")
}

fun f(
    a: ((Int, String) -> String)?
) {
    a?.invoke(1 , "str")
}

fun str(a: Int, b: String): String {
    return b
}

// passLambda()
fun passLambda() {
    val a: (Int, String) -> String = { int, str ->
        println()
        int.toString() + str
    }

    val peoples = listOf(Human("Oleg", 23), Human("Olga", 20))

    val olga = findFirst(peoples) {
        it.name == "Olga"
    }


    println(olga)

    val olgaStd = peoples.first {
        it.name == "Olga"
    }
}

// *** *** ***

fun olgaFinder(human: Human) = human.name == "Olga"

fun methodReference() {
    val people = listOf(Human("Oleg", 23), Human("Olga", 20))

    // ❌
    val olgaWithoutMemberReference = findFirst(people) {
        olgaFinder(it)
    }

    // ✅
    val olga = findFirst(people, ::olgaFinder)

    println("$olgaWithoutMemberReference $olga")
}

package com.pa_vel.kotlin

fun worksWithListNumber(list: List<Number>) {
    println(list)
}

fun worksWithGenericNumber(genericClass: GenericClass<Number>) {
    println(genericClass)
}

fun main() {
    val listOfInt = listOf(1, 2, 3)
//    worksWithListNumber(listOfInt)

    val genericInt = GenericClass(1)
//    worksWithGenericNumber(genericInt)

    val anyComparator = Comparator<Any> { e1, e2 ->
        e1.hashCode() - e2.hashCode()
    }

    val list = listOf("Python", "Kotlin", "Java")
    list.sortedWith(
        Comparator() { e1, e2 -> e1.length - e2.length
        }
    )
    // String <- Any => Comparator<Any> <- Comparator<String>
    list.sortedWith(
            Comparator() { e1, e2 -> e1.hashCode() - e2.hashCode()
        }
    )
}
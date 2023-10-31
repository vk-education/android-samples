//iterables()
fun iterables() {
    val iterable: Iterable<Int> = setOf(1, 2 ,3)

    val iterator = iterable.iterator()
    while (iterator.hasNext()) {
        println(iterator.next())
    }
}
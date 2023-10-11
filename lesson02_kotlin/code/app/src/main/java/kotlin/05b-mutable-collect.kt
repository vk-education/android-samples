//mutableCollections()
fun mutableCollections() {
    val mut = mutableListOf(1, 2, 3)
    val immut = listOf(1, 2, 3)

//    immut.add(4)
//    immut.remove(4)

    mut.add(4)
    mut.remove(4)

    val forceImmut: List<Int> = mut

//     forceImmut.add(5)

    val someList = listOf(1, 2, 3)
//    someList.add(4)
    println(someList)
}
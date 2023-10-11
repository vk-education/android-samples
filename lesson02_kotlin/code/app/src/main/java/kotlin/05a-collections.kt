//listSet()
fun main() {
    mapDemo()
}

fun listSet() {
    val list = listOf(1, 2, 3, 3)
    val set = setOf(1, 2, 3, 3)

    println(list.javaClass.simpleName)
    println(set.javaClass.simpleName)

    println("list: $list")
    println("set: $set")

    val secondEl = list[1]
//    val secondElInSet = set.get(1)

    val secondElInSet = if (set.size >= 2) {
        val iter = set.iterator()
        iter.next()
        iter.next()
    } else {
        null
    }
    println("second in set: $secondElInSet")
}

//mapDemo()
fun mapDemo() {
    val map: Map<String, Int> = mapOf("a" to 1, "b" to 2)
    println(map)

    println("a: ${map.containsKey("a")}: ${map["a"]}")
    println("c: ${map.containsKey("c")}: ${map["c"]}")

    println(map.javaClass.simpleName)
}
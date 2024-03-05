fun main(args: Array<String>) {

    val node = Node(null)

    try {
        node.next?.next?.next?.next?.next
    } catch (e: NullPointerException) {
        // pff
    }

}

class Node(
    val next: Node?
)
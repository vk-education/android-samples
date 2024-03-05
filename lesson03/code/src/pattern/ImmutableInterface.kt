package pattern

fun main() {
    val point2D = Point2D(5, 6)

    point2D.x = 7
    point2D.y = 1

    val immutablePoint2D: ImmutablePoint2D = Point2D(1, 1)
//    immutablePoint2D.x = 5
}

interface ImmutablePoint2D {
    val x: Int
    val y: Int
}

class Point2D(override var x: Int, override var y: Int) : ImmutablePoint2D
package pattern.structural


fun main() {
    val shapes = arrayOf<Shape>(
        Circle(5, 10, 10, LargeCircleDrawer()),
        Circle(20, 30, 100, SmallCircleDrawer())
    )

    for (next in shapes) next.draw()
}

interface Drawer {
    fun drawCircle(x: Int, y: Int, radius: Int)
}

class SmallCircleDrawer : Drawer {
    override fun drawCircle(x: Int, y: Int, radius: Int) {
        println("Small circle center = " + x + "," + y + " radius = " + radius * radiusMultiplier)
    }

    companion object {
        const val radiusMultiplier: Double = 0.25
    }
}

class LargeCircleDrawer : Drawer {
    override fun drawCircle(x: Int, y: Int, radius: Int) {
        println("Large circle center = " + x + "," + y + " radius = " + radius * radiusMultiplier)
    }

    companion object {
        const val radiusMultiplier: Int = 10
    }
}

abstract class Shape protected constructor(protected var drawer: Drawer) {
    abstract fun draw()

    abstract fun enlargeRadius(multiplier: Int)
}

class Circle(x: Int, y: Int, radius: Int, drawer: Drawer) : Shape(drawer) {
    var x: Int = 0
    var y: Int = 0
    var radius: Int = 0

    init {
        this.x = x
        this.y = y
        this.radius = radius
    }

    override fun draw() {
        drawer.drawCircle(x, y, radius)
    }

    override fun enlargeRadius(multiplier: Int) {
        radius *= multiplier
    }
}
package pattern.structural

fun main() {
    //Initialize four ellipses
    val ellipse1 = Ellipse()
    val ellipse2 = Ellipse()
    val ellipse3 = Ellipse()
    val ellipse4 = Ellipse()

    //Initialize three composite graphics
    val graphic = CompositeGraphic()
    val graphic1 = CompositeGraphic()
    val graphic2 = CompositeGraphic()

    //Composes the graphics
    graphic1.add(ellipse1)
    graphic1.add(ellipse2)
    graphic1.add(ellipse3)

    graphic2.add(ellipse4)

    graphic.add(graphic1)
    graphic.add(graphic2)

    //Prints the complete graphic (four times the string "Ellipse").
    graphic.print()
}

/** "Component"  */
interface Graphic {
    //Prints the graphic.
    fun print()
}

/** "Composite"  */
class CompositeGraphic : Graphic {
    //Collection of child graphics.
    private val childGraphics: MutableList<Graphic> = ArrayList()

    //Prints the graphic.
    override fun print() {
        for (graphic in childGraphics) {
            graphic.print()
        }
    }

    //Adds the graphic to the composition.
    fun add(graphic: Graphic) {
        childGraphics.add(graphic)
    }

    //Removes the graphic from the composition.
    fun remove(graphic: Graphic) {
        childGraphics.remove(graphic)
    }
}


/** "Leaf"  */
class Ellipse : Graphic {
    //Prints the graphic.
    override fun print() {
        println("Ellipse")
    }
}

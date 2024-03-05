package pattern.creational

fun main() {
    val carBuildr = Car.Builder()
    carBuildr.model = "Tesla"
    carYear(carBuildr)
    println(carBuildr.build())
}

fun carYear(carBuilder: Car.Builder) {
    carBuilder.year = 2024
}

data class Car(
    val model: String?,
    val year: Int
) {

    private constructor(builder: Builder) : this(builder.model, builder.year)

    class Builder {
        var model: String? = null
        var year: Int = 0

        fun build() = Car(this)
    }
}
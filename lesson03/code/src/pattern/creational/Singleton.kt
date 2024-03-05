package pattern.creational

fun main() {
    println("I need unique car")
    val newCar = UniqueCar.getInstance("LadaQuatro", 2024)
    val sameCar = UniqueCar.getInstance("asda", 2025)
    val another = UniqueCar.getInstance()
    println(newCar == sameCar)
    println(newCar == another)

}

class UniqueCar private constructor(
    val model: String,
    val year: Int,
) {
    companion object {

        @Volatile
        private var INSTANCE: UniqueCar? = null

        fun getInstance(model: String? = null, year: Int? = null): UniqueCar =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: run {
                    checkNotNull(model)
                    checkNotNull(year)
                    UniqueCar(model, year).also { INSTANCE = it }
                }
            }
    }
}
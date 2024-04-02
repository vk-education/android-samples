package company.vk.lesson06.businesslayer

class FactorialCalculator: ICalculator {
    override fun calculate(value: String): String {
        if (value.isBlank()) {
            return "Blank"
        }

        val number = value.toIntOrNull()

        if (number == null) {
            return "Not a number"
        }

        if (number < 0) {
            return "Is negative number"
        }

        var sum = 1
        for (i in 2 .. number) {
            sum *= i
        }

        return sum.toString()
    }
}
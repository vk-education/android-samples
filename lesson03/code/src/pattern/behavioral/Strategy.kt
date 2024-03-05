package pattern.behavioral

fun main() {
    val context = Context()

    context.setStrategy(ConcreteStrategyAdd())
    val resultA = context.executeStrategy(3, 4)

    context.setStrategy(ConcreteStrategySubtract())
    val resultB = context.executeStrategy(3, 4)

    context.setStrategy(ConcreteStrategyMultiply())
    val resultC = context.executeStrategy(3, 4)

    println("Result A : $resultA")
    println("Result B : $resultB")
    println("Result C : $resultC")
}

interface Strategy {
    fun execute(a: Int, b: Int): Int
}

class ConcreteStrategyAdd : Strategy {
    override fun execute(a: Int, b: Int): Int {
        println("Called ConcreteStrategyAdd's execute()")
        return a + b // Do an addition with a and b
    }
}

class ConcreteStrategySubtract : Strategy {
    override fun execute(a: Int, b: Int): Int {
        println("Called ConcreteStrategySubtract's execute()")
        return a - b // Do a subtraction with a and b
    }
}

class ConcreteStrategyMultiply : Strategy {
    override fun execute(a: Int, b: Int): Int {
        println("Called ConcreteStrategyMultiply's execute()")
        return a * b // Do a multiplication with a and b
    }
}

class Context {
    private var strategy: Strategy? = null

    // Set new strategy
    fun setStrategy(strategy: Strategy) {
        this.strategy = strategy
    }

    fun executeStrategy(a: Int, b: Int): Int {
        return strategy!!.execute(a, b)
    }
}

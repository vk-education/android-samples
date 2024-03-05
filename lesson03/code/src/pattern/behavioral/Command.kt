package pattern.behavioral

import java.util.*

fun main() {
    val navigator = Navigator().registerStep(GoEastCommand())
        .registerStep(GoEastCommand())
        .registerStep(GoSouthCommand())
    navigator.go()
    println("Arrived")
}

class Steps {
    fun goSouth() {
        println("step to south")
    }

    fun goNorth() {
        println("step to north")
    }

    fun goEast() {
        println("step to east")
    }

    fun goWest() {
        println("step to west")
    }
}

abstract class StepsCommand : Command {
    protected var steps: Steps = Steps()
}

interface Command {
    fun execute()
}

class GoEastCommand : StepsCommand() {
    override fun execute() {
        steps.goEast()
    }
}

class GoNorthCommand : StepsCommand() {
    override fun execute() {
        steps.goNorth()
    }
}

class GoSouthCommand : StepsCommand() {
    override fun execute() {
        steps.goSouth()
    }
}

class GoWestCommand : StepsCommand() {
    override fun execute() {
        steps.goWest()
    }
}

class Navigator {
    private val steps: MutableList<StepsCommand> = LinkedList()

    fun registerStep(step: StepsCommand): Navigator {
        steps.add(step)
        return this
    }

    fun go() {
        for (step in steps) {
            step.execute()
        }
        steps.clear()
    }
}

package pattern.behavioral

fun main() {
    val m = ConcreteMediator()

    val c1 = ConcreteColleague1(m)
    val c2 = ConcreteColleague2(m)

    m.setColleague1(c1)
    m.setColleague2(c2)

    c1.send("How are you?")
    c2.send("Fine, thanks")
}

abstract class Colleague(private val mediator: Mediator) {
    fun send(message: String) {
        mediator.send(message, this)
    }

    abstract fun notify(message: String)
}

abstract class Mediator {
    abstract fun send(message: String, sender: Colleague)
}

class ConcreteColleague1(mediator: Mediator) : Colleague(mediator) {
    override fun notify(message: String) {
        println("Colleague1 gets message: $message")
    }
}

class ConcreteColleague2(mediator: Mediator) : Colleague(mediator) {
    override fun notify(message: String) {
        println("Colleague2 gets message: $message")
    }
}

class ConcreteMediator : Mediator() {
    private var colleague1: ConcreteColleague1? = null
    private var colleague2: ConcreteColleague2? = null

    fun setColleague1(colleague: ConcreteColleague1) {
        this.colleague1 = colleague
    }

    fun setColleague2(colleague: ConcreteColleague2) {
        this.colleague2 = colleague
    }

    override fun send(message: String, sender: Colleague) {
        if (sender == colleague1) {
            colleague2!!.notify(message)
        } else {
            colleague1!!.notify(message)
        }
    }
}

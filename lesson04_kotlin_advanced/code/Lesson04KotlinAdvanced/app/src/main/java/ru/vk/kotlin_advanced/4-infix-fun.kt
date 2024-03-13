

fun main() {
    val acc = Account(1000)
    acc put 150
    // равноценно вызову
    acc.put(150)
    acc.printSum()  // 1300
}
class Account(private var sum: Int) {
    infix fun put(amount: Int){
        sum += amount
    }
    fun printSum() = println(sum)
}

// Инвариантность - классы не связаны, по умолчанию все дженерики инвариантны

class Box<T: Animal>(t: T) {
    var value = t
}

fun test() {
    val box = Box(Cat())
}


// Ковариатность out
// Класс производитель
// производит экземпляры типа T, но не потребляет их.
class Cage <out T : Animal>(val animal: T)

open class Animal
class Cat : Animal()
class Dog: Animal()


fun main() = runExample(exampleNumber = 2)

fun runExample(exampleNumber: Int) {
    when (exampleNumber) {
        1 -> covarianceExample()
        2 -> contravarianceExample()
        3 -> whereExample()
    }
}

private fun covarianceExample() {
    val a: Animal = Cat()
    var regularCage: Cage<Animal> = Cage<Cat>(Cat())  //так можно
    println(regularCage.animal)
    //regularCage = Cage<Dog>(Cat())  //так нельзя
}


//Класс потребитель
//Необязательно T может быть все что угодно
//может только потребляться, но не может производиться.
open class Food
class Cheese: Food()
class Meat: Food()
class Vegetables: Food()

class Eater<in FOOD : Food>() {
    fun consume(a: FOOD) {
        println("Hroom")
    }
}

private fun contravarianceExample() {
    val cheeseEater: Eater<Cheese> = Eater()
    cheeseEater.consume(Cheese())     //хрум
    //cheeseEater.consume(Food())  // такое не ем
    //cheeseEater.consume(Vegetables())    // не вкусно
}

interface HasBreed {
    fun breedName()
}

interface HasOwner {
    fun ownerName()
}

class SuperCat : Animal(), HasBreed, HasOwner {
    override fun breedName() {}
    override fun ownerName() {}
}

//Через Where мы перечисляем что должен реализовывать T, набор условий
// Элитная клетка только для породистых животных с хозяином!
class EliteCage<out T>(val animal: T) where T : Animal, T : HasBreed, T : HasOwner
private fun whereExample() {
    val a1 = EliteCage(SuperCat())
 //   val b1 = EliteCage(Cat()) //Клетки не для таких
}

// Звездная проекция <*>
// позволяет читать значения (Any?),
// но не дает возможности что‑либо записывать.
fun <T> getSize(list: List<T>): Int {
    return list.size
}

//И его полный аналог с Star
fun getSizeStar(list: List<*>): Int {
    return list.size
}
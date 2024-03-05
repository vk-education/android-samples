package pattern.creational

fun main() {
    val bowFactory: WeaponFactory = Bow.BowFactory
    val client1 = Client(bowFactory)
    client1.execute()

    val pistolFactory: WeaponFactory = Pistol.PistolFactory
    val client2 = Client(pistolFactory)
    client2.execute()
}

class Client(factory: WeaponFactory) {
    private val weapon = factory.createWeapon()

    fun execute() {
        weapon.fire()
    }
}

interface WeaponFactory {
    fun createWeapon(): Weapon
}

interface Weapon {
    fun fire()
}

class Bow : Weapon {
    override fun fire() {
        println(this.javaClass.name)
    }

    companion object BowFactory : WeaponFactory {
        override fun createWeapon(): Weapon = Bow()
    }
}

class Pistol : Weapon {
    override fun fire() {
        println(this.javaClass.name)
    }

    companion object PistolFactory : WeaponFactory {
        override fun createWeapon(): Weapon = Pistol()
    }
}


package pattern.creational

fun main() {
    factoryMethod(Ammo.Bullet).fire()
    factoryMethod(Ammo.Arrow).fire()
}

fun factoryMethod(ammo: Ammo): Weapon {
    return when (ammo) {
        Ammo.Arrow -> Bow()
        Ammo.Bullet -> Pistol()
    }
}

sealed interface Ammo {
    data object Arrow : Ammo
    data object Bullet : Ammo
}
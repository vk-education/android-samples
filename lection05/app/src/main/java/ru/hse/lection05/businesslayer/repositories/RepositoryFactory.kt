package ru.hse.lection05.businesslayer.repositories

object RepositoryFactory {
    interface IFactory {
        fun <T> acqure(clazz: Class<T>): T?
    }

    private val factories = mutableSetOf<IFactory>()


    fun register(factory: IFactory) {
        factories.add(factory)
    }

    fun <T> acqure(clazz: Class<T>): T {
        factories.forEach {
            val tmp = it.acqure(clazz)
            if (tmp != null) {
                return tmp
            }
        }

        throw IllegalStateException("cant create implementation: $clazz")
    }
}
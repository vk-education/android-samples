package company.vk.lesson06

import java.util.concurrent.ConcurrentLinkedDeque

object ServiceLocator {
    interface IFactory {
        fun <T> inject(clazz: Class<T>): T?
    }

    private val factories = ConcurrentLinkedDeque<IFactory>()

    fun register(factory: IFactory) {
        factories.push(factory)
    }

    fun unregister(factory: IFactory) {
        factories.remove(factory)
    }

    fun <T> inject(clazz: Class<T>): T {
        factories.forEach { factory ->
            factory.inject(clazz)?.let { instance ->
                return instance
            }
        }

        throw IllegalStateException("Instance for '$clazz' is not found")
    }

    inline fun <reified T> inject(): T {
        return inject(T::class.java)
    }
}

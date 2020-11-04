package ru.hse.lection05.mvvm.simple.businesslayer

import java.util.*

class ProviderFactory {
    companion object {
        protected val factories: Queue<IFactory> = LinkedList()


        fun add(factory: IFactory) {
            factories.add(factory)
        }

        fun <PROVIDER> get(clazz: Class<PROVIDER>): PROVIDER? {
            factories.forEach {factory ->
                factory.create(clazz)?.let {
                    return it
                }
            }

            return null
        }
    }


    interface IFactory {
        fun <PROVIDER> create(clazz: Class<PROVIDER>): PROVIDER?
    }
}
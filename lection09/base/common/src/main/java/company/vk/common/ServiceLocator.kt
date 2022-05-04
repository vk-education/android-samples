package company.vk.common

import company.vk.base.loggers.Logger
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentLinkedDeque

object ServiceLocator {
	interface IFactory {
		fun create(clazz: Class<*>): Any?
	}


	private val instances = ConcurrentHashMap<Any, Any>()
	private val factories = ConcurrentLinkedDeque<IFactory>()


	init {
		addFactory(EmptyConstructorFactory())
	}


	fun <INSTANCE> inject(clazz: Class<INSTANCE>): INSTANCE {
		return findOrCreate(clazz) as INSTANCE
	}

	inline fun <reified INSTANCE> inject() = lazy {
		val clazz = INSTANCE::class.java
		inject(clazz)
	}

	fun addFactory(factory: IFactory) {
		factories.push(factory)
	}


	private fun findOrCreate(clazz: Class<*>): Any {
		instances[clazz]?.let {
			Logger.d("For $clazz returned $it (cache)")
			return it
		}

		val instance = create(clazz)!!
		instances[clazz] = instance
		Logger.d("For $clazz returned $instance (new)")

		return instance
	}

	private fun create(clazz: Class<*>): Any? {
		factories.forEach { factory ->
			factory.create(clazz)?.let { implementation ->
				return implementation
			}
		}

		return null
	}
}
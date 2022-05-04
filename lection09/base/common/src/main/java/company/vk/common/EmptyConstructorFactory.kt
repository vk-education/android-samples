package company.vk.common

class EmptyConstructorFactory: ServiceLocator.IFactory {
	override fun create(clazz: Class<*>): Any? {
		return clazz.newInstance()
	}
}
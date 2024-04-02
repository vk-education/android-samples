package company.vk.lesson06

class FakeFactory: ServiceLocator.IFactory {
    protected val fakes = mutableMapOf<Any, Any>()

    override fun <T> inject(clazz: Class<T>): T? {
        return fakes[clazz.name] as? T
    }

    fun add(clazz: Class<*>, result: Any) {
        fakes[clazz.name] = result
    }
}
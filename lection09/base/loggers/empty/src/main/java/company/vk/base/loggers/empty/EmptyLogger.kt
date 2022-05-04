package company.vk.base.loggers.empty

import company.vk.base.loggers.Logger

class EmptyLogger: Logger.ILogger {
	override fun d(message: String, tag: String?) = Unit
	override fun e(error: Throwable, tag: String?) = Unit
}
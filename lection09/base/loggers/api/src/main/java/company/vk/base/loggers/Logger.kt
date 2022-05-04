package company.vk.base.loggers

object Logger {
	interface ILogger {
		fun d(message: String, tag: String? = null)
		fun e(error: Throwable, tag: String? = null)
	}


	private lateinit var logger: ILogger


	fun initialize(logger: ILogger) {
		this.logger = logger
	}


	fun d(message: String, tag: String? = null) {
		logger.d(message, tag)
	}

	fun e(error: Throwable, tag: String? = null) {
		logger.e(error, tag)
	}
}
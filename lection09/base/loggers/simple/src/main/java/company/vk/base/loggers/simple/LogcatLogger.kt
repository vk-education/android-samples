package company.vk.base.loggers.simple

import android.util.Log
import company.vk.base.loggers.Logger

class LogcatLogger: Logger.ILogger {
	override fun d(message: String, tag: String?) {
		Log.d(tag, message)
	}

	override fun e(error: Throwable, tag: String?) {
		Log.e(tag, error.message, error)
	}
}
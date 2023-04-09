package company.vk.Lesson06.misc

import androidx.test.espresso.IdlingResource

class WaitIdlingResource: IdlingResource {
	protected var callback: IdlingResource.ResourceCallback? = null
	protected var startWaitTime = 0L

	override fun getName(): String {
		return "WaitIdlingResource"
	}

	override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback?) {
		this.callback = callback
	}

	override fun isIdleNow(): Boolean {
		val isIdle = System.currentTimeMillis() - startWaitTime > 2000
		if (isIdle) {
			callback?.onTransitionToIdle()
		}

		return isIdle
	}

	fun reset() {
		startWaitTime = System.currentTimeMillis()
	}
}
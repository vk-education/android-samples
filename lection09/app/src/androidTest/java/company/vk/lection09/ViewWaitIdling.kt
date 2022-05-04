package company.vk.lection09

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.ViewFinder
import org.hamcrest.Matcher


class ViewWaitIdling(val viewMatcher: Matcher<View>): IdlingResource  {
	private var resourceCallback: IdlingResource.ResourceCallback? = null

	override fun isIdleNow(): Boolean {
		val view = getView(viewMatcher)
		val idle = view == null || view.isShown
		if (idle && resourceCallback != null) {
			resourceCallback!!.onTransitionToIdle()
		}
		return idle
	}

	override fun registerIdleTransitionCallback(resourceCallback: IdlingResource.ResourceCallback?) {
		this.resourceCallback = resourceCallback
	}

	override fun getName(): String {
		return viewMatcher.toString()
	}

	private fun getView(viewMatcher: Matcher<View>?): View? {
		return try {
			val viewInteraction = onView(viewMatcher)
			val finderField = viewInteraction.javaClass.getDeclaredField("viewFinder")
			finderField.setAccessible(true)
			val finder = finderField.get(viewInteraction) as ViewFinder
			finder.view
		} catch (e: Exception) {
			null
		}
	}
}
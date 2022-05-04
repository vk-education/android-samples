package company.vk.lection09

import android.view.View
import androidx.test.espresso.PerformException
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.util.HumanReadables
import androidx.test.espresso.util.TreeIterables
import org.hamcrest.Matcher
import java.util.concurrent.TimeoutException

class ViewWaitAction(val viewId: Int, val timeoutInMilliseconds: Long = 5000): ViewAction {
	override fun getConstraints(): Matcher<View?>? {
		return isRoot()
	}

	override fun getDescription(): String {
		return "wait for a specific view with id <$viewId> is hidden during $timeoutInMilliseconds millis."
	}

	override fun perform(uiController: UiController, view: View?) {
		uiController.loopMainThreadUntilIdle()
		val startTime = System.currentTimeMillis()
		val endTime = startTime + timeoutInMilliseconds
		val viewMatcher = withId(viewId)
		do {
			for (child in TreeIterables.breadthFirstViewTraversal(view)) {
				// found view with required ID
				if (viewMatcher.matches(child) && child.isShown) {
					return
				}
			}
			uiController.loopMainThreadForAtLeast(50)
		} while (System.currentTimeMillis() < endTime)
		throw PerformException.Builder()
			.withActionDescription(this.description)
			.withViewDescription(HumanReadables.describe(view))
			.withCause(TimeoutException())
			.build()
	}
}
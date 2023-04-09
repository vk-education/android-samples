package company.vk.Lesson06.misc

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.PerformException
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.util.HumanReadables
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import java.util.concurrent.TimeoutException

class RecyclerWaitDataAction(val timeout: Long = 10000L): ViewAction {
	override fun getConstraints(): Matcher<View?>? {
		return allOf(ViewMatchers.isAssignableFrom(RecyclerView::class.java), ViewMatchers.isDisplayed())
	}

	override fun getDescription(): String {
		return "RecyclerWaitDataAction"
	}

	override fun perform(uiController: UiController, view: View) {
		val recyclerView = view as RecyclerView
		val adapter = recyclerView.adapter!!

		val endTime = System.currentTimeMillis() + timeout
		do {
			if (adapter.itemCount > 0) return
			uiController.loopMainThreadForAtLeast(50)
		} while (System.currentTimeMillis() < endTime)

		throw PerformException.Builder()
			.withActionDescription(description)
			.withCause(TimeoutException("Waited $timeout milliseconds"))
			.withViewDescription(HumanReadables.describe(view))
			.build()
	}
}
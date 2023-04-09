package company.vk.Lesson06.misc

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.matcher.BoundedMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher

class RecyclerPositionMatcher(val position: Int, val matcher: Matcher<View>): BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {
	override fun describeTo(description: Description) {
		description.appendText("has item at position $position: ")
		matcher.describeTo(description)
	}

	override fun matchesSafely(view: RecyclerView): Boolean {
		val viewHolder = view.findViewHolderForAdapterPosition(position)
		if (viewHolder == null) {
			return false
		}

		return matcher.matches(viewHolder.itemView)
	}
}
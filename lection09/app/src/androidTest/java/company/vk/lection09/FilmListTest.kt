package company.vk.lection09

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import company.vk.lection09.presentationlayer.MainActivity
import company.vk.lection09.presentationlayer.adapters.holders.FilmHolder

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class FilmListTest {
	@get:Rule
	var activityScenarioRule = activityScenarioRule<MainActivity>()


	@Test
	fun check_click_10_position() {
		onView(withId(R.id.film_list)).perform(click())

//		onView(isRoot()).perform(ViewWaitAction(R.id.recycler))
		val waitRecyclerIdling = ViewWaitIdling(withId(R.id.recycler))
		IdlingRegistry.getInstance().register(waitRecyclerIdling)

		onView(withId(R.id.recycler))
			.perform(actionOnItemAtPosition<FilmHolder>(10, click()))

	}
}
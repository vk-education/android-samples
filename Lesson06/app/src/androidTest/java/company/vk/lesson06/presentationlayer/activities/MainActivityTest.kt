package company.vk.Lesson06.presentationlayer.activities

import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import company.vk.Lesson06.presentationlayer.activities.MainActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule

@RunWith(AndroidJUnit4::class)
class MainActivityTest {
	@get:Rule
	var activityScenarioRule = activityScenarioRule<MainActivity>()

	@Test
	fun startup() {
		onView(withText("FIELDS")).check(matches(isDisplayed()))
		onView(withText("LIST")).check(matches(isDisplayed()))
	}

	@Test
	fun navigate_to_fields(){
		onView(withText("FIELDS")).check(matches(isDisplayed())).perform(click())
		onView(withText("ACTION")).check(matches(isDisplayed()))
		onView(withHint("Field")).check(matches(isDisplayed()))
	}
}
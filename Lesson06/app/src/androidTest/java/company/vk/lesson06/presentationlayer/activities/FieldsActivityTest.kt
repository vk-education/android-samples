package company.vk.Lesson06.presentationlayer.activities

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import company.vk.Lesson06.R
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FieldsActivityTest {
	@get:Rule
	var activityScenarioRule = activityScenarioRule<FieldsActivity>()

	@Before
	fun setUp() {

	}

	@After
	fun tearDown() {

	}

	@Test
	fun check_fields() {
		onView(withHint("Field")).perform(typeText("4"))
		onView(withText("Action")).perform(click())
		onView(withId(R.id.text)).check(matches(withText("24")))
	}
}
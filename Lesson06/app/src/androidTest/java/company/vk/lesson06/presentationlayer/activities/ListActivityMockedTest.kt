package company.vk.Lesson06.presentationlayer.activities

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import company.vk.Lesson06.R
import company.vk.Lesson06.ServiceLocator
import company.vk.Lesson06.datalyaer.ICharacterAccessor
import company.vk.Lesson06.misc.WaitIdlingResource
import company.vk.Lesson06.misc.RecyclerPositionMatcher
import company.vk.Lesson06.misc.RecyclerWaitDataAction
import company.vk.Lesson06.datalayer.FakeCharacterAccessor
import company.vk.Lesson06.FakeFactory
import company.vk.Lesson06.objects.Item
import company.vk.Lesson06.objects.PagingResult
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class ListActivityMockedTest {
	@get:Rule
	var activityScenarioRule = activityScenarioRule<ListActivity>()

	protected val currentIdlingResource = WaitIdlingResource()

	@Before
	fun setUp() {
		currentIdlingResource.reset()
		IdlingRegistry.getInstance().register(currentIdlingResource)
	}

	@After
	fun tearDown() {
		IdlingRegistry.getInstance().unregister(currentIdlingResource)
	}

	@Test
	fun check_data_at_position() {
		onView(withId(R.id.recycler)).perform(RecyclerWaitDataAction())
		onView(withId(R.id.recycler)).perform(RecyclerViewActions.scrollToPosition<ViewHolder>(7))
		onView(withId(R.id.recycler)).check(matches(RecyclerPositionMatcher(7, hasDescendant(withText("Fake Name 7")))))
		onView(withId(R.id.recycler)).check(matches(RecyclerPositionMatcher(7, hasDescendant(withText("Fake Species 7")))))

		// check calls
		val mockedCharacterAccessor = ServiceLocator.inject(ICharacterAccessor::class.java)
		coVerify(exactly = 1) { mockedCharacterAccessor.list(0, 20) }
	}


	companion object {
		protected val fakeFactory by lazy { createFakeFactory() }

		@BeforeClass
		@JvmStatic
		fun globalSetup() {
			ServiceLocator.register(fakeFactory)
		}
		@BeforeClass
		@JvmStatic
		fun globalShutdown() {
			ServiceLocator.unregister(fakeFactory)
		}

		protected fun createFakeFactory(): ServiceLocator.IFactory {
			// just for data generation
			val fakeCharacterAccessor = FakeCharacterAccessor()
			val tmp = object : ICharacterAccessor {
				override suspend fun list(page: Int, count: Int): PagingResult<Item> {
					return fakeCharacterAccessor.list(page, count)
				}
			}

			val mockedCharacterAccessor = spyk(tmp)

			// эта механика работает не везде
//			val mockedCharacterAccessor = mockk<ICharacterAccessor> {
//				coEvery { list(any(), any()) } returns  runBlocking { fakeCharacterAccessor.list(0, 20) }
//			}

			val fakeFactory = FakeFactory().apply {
				add(ICharacterAccessor::class.java, mockedCharacterAccessor)
			}

			return fakeFactory
		}
	}
}
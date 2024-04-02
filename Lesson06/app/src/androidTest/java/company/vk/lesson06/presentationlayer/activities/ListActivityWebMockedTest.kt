package company.vk.lesson06.presentationlayer.activities

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import company.vk.lesson06.R
import company.vk.lesson06.ServiceLocator
import company.vk.lesson06.misc.*
import company.vk.lesson06.ResponseUtils.successResponse
import company.vk.lesson06.FakeFactory
import company.vk.lesson06.datalayer.ICharacterAccessor
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.*
import org.junit.runner.RunWith
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


@RunWith(AndroidJUnit4::class)
class ListActivityWebMockedTest {
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
        enqueueResponse(successResponse("character_0_20.json"))

        onView(withId(R.id.recycler)).apply {
            perform(RecyclerWaitDataAction())
            perform(RecyclerViewActions.scrollToPosition<ViewHolder>(7))
            check(matches(RecyclerPositionMatcher(7, hasDescendant(withText("Mocked Item")))))
            check(matches(RecyclerPositionMatcher(7, hasDescendant(withText("Mocked Species")))))
        }
    }

    companion object {
        protected val mockedWebServer by lazy { createMockWebServer() }

        @BeforeClass
        @JvmStatic
        fun globalSetup() {
            val baseUrl = mockedWebServer.url("")
            val fakeRetrofit = createFakeRetrofit(baseUrl)
            val fakeCharacterAccessor = fakeRetrofit.create(ICharacterAccessor::class.java)

            val fakeFactory = FakeFactory().apply {
                add(ICharacterAccessor::class.java, fakeCharacterAccessor)
            }

            ServiceLocator.register(fakeFactory)
        }

        @AfterClass
        @JvmStatic
        fun globalShutdown() {
            mockedWebServer.shutdown()
        }

        fun enqueueResponse(response: MockResponse) {
            mockedWebServer.enqueue(response)
        }

        protected fun createMockWebServer(): MockWebServer {
            return MockWebServer().apply {
                start()
            }
        }

        protected fun createFakeRetrofit(baseUrl: HttpUrl): Retrofit {
            val loggingInterceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

            val client = OkHttpClient.Builder().apply {
                addInterceptor(loggingInterceptor)
                readTimeout(10, TimeUnit.SECONDS)
                writeTimeout(10, TimeUnit.SECONDS)
                callTimeout(10, TimeUnit.SECONDS)
            }.build()

            val retrofit = Retrofit.Builder().apply {
                addConverterFactory(GsonConverterFactory.create())
                baseUrl(baseUrl)
                client(client)
            }.build()

            return retrofit
        }
    }
}
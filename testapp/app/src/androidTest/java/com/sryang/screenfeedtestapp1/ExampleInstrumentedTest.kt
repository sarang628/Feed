package com.sryang.screenfeedtestapp1

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.example.torang_core.data.model.LoggedInUserData
import com.example.torangrepository.FeedRepositoryImpl
import com.example.torangrepository.LoginRepositoryImpl
import dagger.hilt.android.testing.HiltAndroidRule
import kotlinx.coroutines.runBlocking

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule
import org.junit.rules.RuleChain

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.sryang.screenfeedtestapp1", appContext.packageName)
    }

/*    @Test
    fun test() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val feedRepository = FeedRepositoryImpl(appContext)

        runBlocking {
            feedRepository.loadFeed()
        }
    }*/

    @Test
    fun login() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val loginrepository = LoginRepositoryImpl(appContext)

        runBlocking {
            val loggedInUserData = LoggedInUserData(userId = 4)
            loginrepository.setLoggedInUser(loggedInUserData)
        }
    }

    private val hiltRule = HiltAndroidRule(this)
    private val activityTestRule = ActivityTestRule(MainActivity::class.java)

    @get:Rule
    val rule = RuleChain
        .outerRule(hiltRule)
        .around(activityTestRule)

    @Test fun clickAddPlant_OpensPlantList() {
        // Given that no Plants are added to the user's garden

        // When the "Add Plant" button is clicked
        /*Espresso.onView(withId(R.id.add_plant)).perform(ViewActions.click())*/

        // Then the ViewPager should change to the Plant List page
        /*Espresso.onView(withId(R.id.plant_list))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))*/
    }
}
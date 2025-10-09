package com.sarang.torang

import android.app.Application
import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sarang.torang.compose.feed.FeedScreenByRestaurantId
import com.sarang.torang.compose.feed.FeedScreenInMain
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class FeedScreenByRestaurantIdTest {

    @get:Rule var hiltRule = HiltAndroidRule(this)
    @Before fun setUp() { hiltRule.inject() }
    @get:Rule(order = 1) val composeTestRule = createAndroidComposeRule<HiltTestActivity>()

    @Test
    fun verify_hilt_application_is_used() {
        val app = ApplicationProvider.getApplicationContext<Application>()
        println("App class = ${app::class.java.name}")
        assert(app is HiltTestApplication)
    }

    @Test
    fun FeedScreenByRestaurantIdTest() {
        composeTestRule.setContent {
            FeedScreenByRestaurantId(restaurantId = 234)
        }
    }

    @Test
    fun FeedScreenInMainTest() {
        composeTestRule.setContent {
            FeedScreenInMain()
        }
    }
}
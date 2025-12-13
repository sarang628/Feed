package com.sarang.torang

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sarang.torang.compose.feed.FeedScreenInMain
import dagger.hilt.android.testing.HiltAndroidRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FeedScreenInMainTest {
    @get:Rule var hiltRule = HiltAndroidRule(this)
    @Before fun setUp() { hiltRule.inject() }
    @get:Rule(order = 1) val composeTestRule = createAndroidComposeRule<HiltTestActivity>()

    @Test
    fun feedScreenInMainTest() {
        composeTestRule.setContent {
            FeedScreenInMain()
        }
    }
}
package com.sarang.torang

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.isNotDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sarang.torang.compose.feed.FeedScreen
import com.sarang.torang.uistate.FeedLoadingUiState
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FeedScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun feedScreen_shimmer_test() {
        composeTestRule.setContent { FeedScreen(loadingUiState = FeedLoadingUiState.Loading) }

        // UI에 특정 텍스트가 보이는지 검증
        composeTestRule.onNodeWithTag("shimmer").assertIsDisplayed()
    }

    @Test
    fun feedScreen_shimmer_test1() {
        composeTestRule.setContent { FeedScreen(loadingUiState = FeedLoadingUiState.Success) }

        // UI에 특정 텍스트가 보이는지 검증
        composeTestRule.onNodeWithTag("shimmer").isNotDisplayed()
    }
}
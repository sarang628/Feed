package com.sarang.torang

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sarang.torang.usecase.ClickFavorityUseCase
import com.sarang.torang.usecase.ClickLikeUseCase
import com.sarang.torang.usecase.FeedWithPageUseCase
import com.sarang.torang.usecase.GetFeedFlowUseCase
import com.sarang.torang.usecase.GetFeedLodingFlowUseCase
import com.sarang.torang.compose.feed.FeedsViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class FeedsViewModelTest {

    @get:Rule var hiltRule = HiltAndroidRule(this)
    @Before fun setUp() {
        hiltRule.inject()
        setUpViewModel()
    }

    @Inject lateinit var feedWithPageUseCase : FeedWithPageUseCase
    @Inject lateinit var clickLikeUseCase: ClickLikeUseCase
    @Inject lateinit var clickFavoriteUseCase: ClickFavorityUseCase
    @Inject lateinit var getLoadingFeedFlowUseCase: GetFeedLodingFlowUseCase
    @Inject lateinit var getFeedFlowUseCase: GetFeedFlowUseCase
    lateinit var viewModel : FeedsViewModel
    fun setUpViewModel(){
        viewModel = FeedsViewModel(
            feedWithPageUseCase = feedWithPageUseCase,
            clickLikeUseCase = clickLikeUseCase,
            clickFavoriteUseCase = clickFavoriteUseCase,
            getLoadingFeedFlowUseCase = getLoadingFeedFlowUseCase,
            getFeedFlowUseCase = getFeedFlowUseCase
        )
    }


    @Test
    fun feedsViewModelTest() = runBlocking {
        delay(5000)
        assertTrue(viewModel.feedUiState.list.isNotEmpty())
    }
}
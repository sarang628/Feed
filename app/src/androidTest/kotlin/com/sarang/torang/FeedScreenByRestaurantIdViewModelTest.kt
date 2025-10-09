package com.sarang.torang

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sarang.torang.usecase.ClickFavorityUseCase
import com.sarang.torang.usecase.ClickLikeUseCase
import com.sarang.torang.usecase.FeedWithPageUseCase
import com.sarang.torang.usecase.FindFeedByRestaurantIdFlowUseCase
import com.sarang.torang.usecase.GetFeedByRestaurantIdFlowUseCase
import com.sarang.torang.usecase.GetFeedFlowUseCase
import com.sarang.torang.usecase.GetFeedLodingFlowUseCase
import com.sarang.torang.viewmodels.FeedScreenByRestaurantIdViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.time.withTimeout
import kotlinx.coroutines.withContext
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.time.Duration
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class FeedScreenByRestaurantIdViewModelTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)
    @Before
    fun setUp() {
        hiltRule.inject()
        setUpViewModel()
    }

    @Inject
    lateinit var feedWithPageUseCase: FeedWithPageUseCase
    @Inject
    lateinit var clickLikeUseCase: ClickLikeUseCase
    @Inject
    lateinit var clickFavoriteUseCase: ClickFavorityUseCase
    @Inject
    lateinit var getLoadingFeedFlowUseCase: GetFeedLodingFlowUseCase
    @Inject
    lateinit var getFeedFlowUseCase: GetFeedFlowUseCase
    @Inject
    lateinit var getFeedByRestaurantIdFlowUseCase: GetFeedByRestaurantIdFlowUseCase
    @Inject
    lateinit var findFeedByRestaurantIdFlowUseCase: FindFeedByRestaurantIdFlowUseCase

    lateinit var viewModel: FeedScreenByRestaurantIdViewModel

    fun setUpViewModel() {
        viewModel = FeedScreenByRestaurantIdViewModel(
            feedWithPageUseCase = feedWithPageUseCase,
            clickLikeUseCase = clickLikeUseCase,
            clickFavoriteUseCase = clickFavoriteUseCase,
            getLoadingFeedFlowUseCase = getLoadingFeedFlowUseCase,
            getFeedFlowUseCase = getFeedFlowUseCase,
            getFeedByRestaurantIdFlowUseCase = getFeedByRestaurantIdFlowUseCase,
            findFeedByRestaurantIdFlowUseCase = findFeedByRestaurantIdFlowUseCase
        )
    }


    @Test
    fun feedsViewModelTest() = runTest {
        viewModel.feedUiState1.first() // TODO::문제 상황 정확히 파악해서 수정하기
    }
}
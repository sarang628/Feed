package com.sarang.torang.viewmodels

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.sarang.torang.data.feed.Feed
import com.sarang.torang.uistate.FeedLoadingUiState
import com.sarang.torang.usecase.ClickFavorityUseCase
import com.sarang.torang.usecase.ClickLikeUseCase
import com.sarang.torang.usecase.FeedWithPageUseCase
import com.sarang.torang.usecase.GetFeedFlowUseCase
import com.sarang.torang.usecase.GetFeedLodingFlowUseCase
import com.sarang.torang.usecase.GetMyAllFeedByReviewIdUseCase
import com.sarang.torang.usecase.GetMyFeedFlowUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MyFeedsViewModel @Inject constructor(
    clickLikeUseCase: ClickLikeUseCase,
    clickFavorityUseCase: ClickFavorityUseCase,
    getFeedLoadingFlowUseCase: GetFeedLodingFlowUseCase,
    feedWithPageUseCase: FeedWithPageUseCase,
    private val getMyFeedFlowUseCase: GetMyFeedFlowUseCase,
    private val getMyAllFeedByReviewIdUseCase: GetMyAllFeedByReviewIdUseCase,
    getFeedFlowUseCase: GetFeedFlowUseCase,
) : FeedsViewModel(
    feedWithPageUseCase,
    clickLikeUseCase,
    clickFavorityUseCase,
    getFeedLoadingFlowUseCase,
    getFeedFlowUseCase
) {

    private val _reviewIdState = MutableStateFlow<Int?>(null)

    fun getUserFeedByReviewId(reviewId: Int) {
        Log.d("__MyFeedsViewModel", "getUserFeedByReviewId : $reviewId")
        _reviewIdState.value = reviewId
    }

    override fun refreshFeed() {
        Log.d("__MyFeedsViewModel", "refreshFeed called but nothing to do")
    }

    override fun onBottom() {
        Log.d("__MyFeedsViewModel", "onBottom called but nothing to do")
    }

    fun findIndexByReviewId(list: List<Feed>, reviewId: Int): Int {
        return list.indexOf(list.find { it.reviewId == reviewId })
    }
}
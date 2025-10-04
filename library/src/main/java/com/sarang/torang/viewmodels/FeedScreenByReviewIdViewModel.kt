package com.sarang.torang.viewmodels

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.sarang.torang.data.feed.Feed
import com.sarang.torang.uistate.FeedLoadingUiState
import com.sarang.torang.usecase.ClickFavorityUseCase
import com.sarang.torang.usecase.ClickLikeUseCase
import com.sarang.torang.usecase.FeedWithPageUseCase
import com.sarang.torang.usecase.GetFeedByReviewIdUseCase
import com.sarang.torang.usecase.GetFeedFlowUseCase
import com.sarang.torang.usecase.GetFeedLodingFlowUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import kotlin.collections.indexOf

@HiltViewModel
class FeedScreenByReviewIdViewModel @Inject constructor(
    clickLikeUseCase: ClickLikeUseCase,
    clickFavorityUseCase: ClickFavorityUseCase,
    getFeedLoadingFlowUseCase: GetFeedLodingFlowUseCase,
    feedWithPageUseCase: FeedWithPageUseCase,
    getFeedFlowUseCase: GetFeedFlowUseCase,
    private val getFeedByReviewIdUseCase: GetFeedByReviewIdUseCase,
) : FeedsViewModel(
    feedWithPageUseCase,
    clickLikeUseCase,
    clickFavorityUseCase,
    getFeedLoadingFlowUseCase,
    getFeedFlowUseCase
) {
    val tag: String = "__FeedScreenByReviewIdViewModel"

    private val _reviewIdState = MutableStateFlow<Int?>(null)

    fun getFeedByReviewId(reviewId: Int) {
        _reviewIdState.value = reviewId
    }

    fun findIndexByReviewId(list: List<Feed>, reviewId: Int): Int {
        return list.indexOf(list.find { it.reviewId == reviewId })
    }

    override fun refreshFeed() {
        Log.d("__FeedScreenByReviewIdViewModel", "refreshFeed called but nothing to do")
    }

    override fun onBottom() {
        Log.d("__FeedScreenByReviewIdViewModel", "onBottom called but nothing to do")
    }
}
package com.sarang.torang.viewmodels

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.sarang.torang.data.feed.Feed
import com.sarang.torang.uistate.FeedUiState
import com.sarang.torang.usecase.AddFavoriteUseCase
import com.sarang.torang.usecase.AddLikeUseCase
import com.sarang.torang.usecase.ClickFavorityUseCase
import com.sarang.torang.usecase.ClickLikeUseCase
import com.sarang.torang.usecase.DeleteFavoriteUseCase
import com.sarang.torang.usecase.DeleteLikeUseCase
import com.sarang.torang.usecase.FeedWithPageUseCase
import com.sarang.torang.usecase.GetFeedByReviewIdUseCase
import com.sarang.torang.usecase.GetFeedFlowUseCase
import com.sarang.torang.usecase.IsLoginFlowForFeedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.indexOf

@HiltViewModel
class FeedScreenByReviewIdViewModel @Inject constructor(
    clickLikeUseCase: ClickLikeUseCase,
    clickFavorityUseCase: ClickFavorityUseCase,
    getFeedFlowUseCase: GetFeedFlowUseCase,
    feedWithPageUseCase: FeedWithPageUseCase,
    isLoginFlowUseCase: IsLoginFlowForFeedUseCase,
    private val getFeedByReviewIdUseCase: GetFeedByReviewIdUseCase,
) : FeedsViewModel(
    feedWithPageUseCase,
    clickLikeUseCase,
    clickFavorityUseCase,
    getFeedFlowUseCase,
    isLoginFlowUseCase
) {
    val tag: String = "__FeedScreenByReviewIdViewModel"

    private val _reviewIdState = MutableStateFlow<Int?>(null)

    override val uiState: StateFlow<FeedUiState> =
        _reviewIdState
            .flatMapLatest { reviewId ->
                MutableStateFlow(listOf(getFeedByReviewIdUseCase.invoke(reviewId)))
            }
            .map<List<Feed>, FeedUiState>(FeedUiState::Success)
            .onStart { emit(FeedUiState.Loading) }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), FeedUiState.Loading)

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
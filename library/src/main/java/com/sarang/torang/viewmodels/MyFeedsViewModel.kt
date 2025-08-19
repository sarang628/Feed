package com.sarang.torang.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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
import com.sarang.torang.usecase.GetFeedFlowUseCase
import com.sarang.torang.usecase.GetMyAllFeedByReviewIdUseCase
import com.sarang.torang.usecase.GetMyFeedFlowUseCase
import com.sarang.torang.usecase.IsLoginFlowForFeedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyFeedsViewModel @Inject constructor(
    clickLikeUseCase: ClickLikeUseCase,
    clickFavorityUseCase: ClickFavorityUseCase,
    getFeedFlowUseCase: GetFeedFlowUseCase,
    feedWithPageUseCase: FeedWithPageUseCase,
    private val getMyFeedFlowUseCase: GetMyFeedFlowUseCase,
    private val getMyAllFeedByReviewIdUseCase: GetMyAllFeedByReviewIdUseCase,
    isLoginFlowUseCase: IsLoginFlowForFeedUseCase,
) : FeedsViewModel(
    feedWithPageUseCase,
    clickLikeUseCase,
    clickFavorityUseCase,
    getFeedFlowUseCase,
    isLoginFlowUseCase
) {

    private val _reviewIdState = MutableStateFlow<Int?>(null)

    override val uiState: StateFlow<FeedUiState> =
        _reviewIdState
            .flatMapLatest { reviewId ->
                getMyFeedFlowUseCase.invoke(reviewId)
            }
            .map<List<Feed>, FeedUiState>(FeedUiState::Success)
            .onStart { emit(FeedUiState.Loading) }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), FeedUiState.Loading)

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
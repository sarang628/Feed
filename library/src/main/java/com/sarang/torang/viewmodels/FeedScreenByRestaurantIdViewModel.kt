package com.sarang.torang.viewmodels

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.sarang.torang.data.feed.Feed
import com.sarang.torang.uistate.FeedUiState
import com.sarang.torang.usecase.ClickFavorityUseCase
import com.sarang.torang.usecase.ClickLikeUseCase
import com.sarang.torang.usecase.FeedWithPageUseCase
import com.sarang.torang.usecase.GetFeedByRestaurantIdFlowUseCase
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
import javax.inject.Inject

@HiltViewModel
class FeedScreenByRestaurantIdViewModel @Inject constructor(
    clickLikeUseCase: ClickLikeUseCase,
    clickFavorityUseCase: ClickFavorityUseCase,
    getFeedFlowUseCase: GetFeedFlowUseCase,
    feedWithPageUseCase: FeedWithPageUseCase,
    isLoginFlowUseCase: IsLoginFlowForFeedUseCase,
    private val getFeedByRestaurantIdFlowUseCase: GetFeedByRestaurantIdFlowUseCase,
) : FeedsViewModel(
    feedWithPageUseCase,
    clickLikeUseCase,
    clickFavorityUseCase,
    getFeedFlowUseCase,
    isLoginFlowUseCase
) {

    private val _restaurantIdState = MutableStateFlow<Int?>(null)

    override val uiState: StateFlow<FeedUiState> =
    _restaurantIdState
        .flatMapLatest{ restaurantId ->
            getFeedByRestaurantIdFlowUseCase.invoke(restaurantId)
        }
        .map<List<Feed>, FeedUiState>(FeedUiState::Success)
        .onStart { emit(FeedUiState.Loading) }
        .stateIn(scope = viewModelScope, started = SharingStarted.WhileSubscribed(5_000), initialValue = FeedUiState.Loading)

    fun getFeedByRestaurantId(restaurantId: Int) {
        _restaurantIdState.value = restaurantId
    }

    fun findIndexByReviewId(list: List<Feed>, reviewId: Int): Int {
        return list.indexOf(list.find { it.reviewId == reviewId })
    }

    override fun refreshFeed() {
        Log.d("__FeedScreenByRestaurantIdViewModel", "refreshFeed called but nothing to do")
    }

    override fun onBottom() {
        Log.d("__FeedScreenByRestaurantIdViewModel", "onBottom called but nothing to do")
    }
}
package com.sarang.torang.viewmodels

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.sarang.torang.uistate.FeedUiState
import com.sarang.torang.usecase.AddFavoriteUseCase
import com.sarang.torang.usecase.AddLikeUseCase
import com.sarang.torang.usecase.DeleteFavoriteUseCase
import com.sarang.torang.usecase.DeleteLikeUseCase
import com.sarang.torang.usecase.FeedRefreshUseCase
import com.sarang.torang.usecase.FeedWithPageUseCase
import com.sarang.torang.usecase.GetFeedByRestaurantIdFlowUseCase
import com.sarang.torang.usecase.GetFeedFlowUseCase
import com.sarang.torang.usecase.GetMyFeedFlowUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedScreenByRestaurantIdViewModel @Inject constructor(
    feedRefreshUseCase: FeedRefreshUseCase,
    addLikeUseCase: AddLikeUseCase,
    deleteLikeUseCase: DeleteLikeUseCase,
    addFavoriteUseCase: AddFavoriteUseCase,
    deleteFavoriteUseCase: DeleteFavoriteUseCase,
    getFeedFlowUseCase: GetFeedFlowUseCase,
    feedWithPageUseCase: FeedWithPageUseCase,
    private val getFeedByRestaurantIdFlowUseCase: GetFeedByRestaurantIdFlowUseCase
) : FeedsViewModel(
    feedRefreshUseCase,
    feedWithPageUseCase,
    addLikeUseCase,
    deleteLikeUseCase,
    addFavoriteUseCase,
    deleteFavoriteUseCase,
    getFeedFlowUseCase
) {
    fun getFeedByRestaurantId(restaurantId: Int) {
        Log.d("__FeedScreenByRestaurantIdViewModel", "load feed by restaurantId : ${restaurantId}")
        _uiState.value = FeedUiState.Loading
        viewModelScope.launch {
            getFeedByRestaurantIdFlowUseCase.invoke(restaurantId).collect { list ->
                list.map { review ->
                    review.copy(
                        onLike = { onLike(review.reviewId) },
                        onFavorite = { onFavorite(review.reviewId) }
                    )
                }.let { list ->
                    if (list.isNotEmpty()) {
                        _uiState.update {
                            FeedUiState.Success(list = list)
                        }
                    }
                }
            }
        }
    }

    fun findIndexByReviewId(reviewId: Int): Int {
        val state = uiState.value
        if (state is FeedUiState.Success) {
            return state.list.indexOf(state.list.find { it.reviewId == reviewId })
        }
        return 0
    }
}
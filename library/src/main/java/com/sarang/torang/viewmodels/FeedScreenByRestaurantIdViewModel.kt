package com.sarang.torang.viewmodels

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.sarang.torang.uistate.FeedUiState
import com.sarang.torang.usecase.AddFavoriteUseCase
import com.sarang.torang.usecase.AddLikeUseCase
import com.sarang.torang.usecase.DeleteFavoriteUseCase
import com.sarang.torang.usecase.DeleteLikeUseCase
import com.sarang.torang.usecase.FeedWithPageUseCase
import com.sarang.torang.usecase.GetFeedByRestaurantIdFlowUseCase
import com.sarang.torang.usecase.GetFeedFlowUseCase
import com.sarang.torang.usecase.IsLoginFlowForFeedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedScreenByRestaurantIdViewModel @Inject constructor(
    addLikeUseCase: AddLikeUseCase,
    deleteLikeUseCase: DeleteLikeUseCase,
    addFavoriteUseCase: AddFavoriteUseCase,
    deleteFavoriteUseCase: DeleteFavoriteUseCase,
    getFeedFlowUseCase: GetFeedFlowUseCase,
    feedWithPageUseCase: FeedWithPageUseCase,
    isLoginFlowUseCase: IsLoginFlowForFeedUseCase,
    private val getFeedByRestaurantIdFlowUseCase: GetFeedByRestaurantIdFlowUseCase,
) : FeedsViewModel(
    feedWithPageUseCase,
    addLikeUseCase,
    deleteLikeUseCase,
    addFavoriteUseCase,
    deleteFavoriteUseCase,
    getFeedFlowUseCase,
    isLoginFlowUseCase
) {
    fun getFeedByRestaurantId(restaurantId: Int) {
        uiState = FeedUiState.Loading
        viewModelScope.launch {
            try {
                getFeedByRestaurantIdFlowUseCase.invoke(restaurantId).collect { list ->
                    list.map { review ->
                        review.copy(
                            onLike = { onLike(review.reviewId) },
                            onFavorite = { onFavorite(review.reviewId) }
                        )
                    }.let { list ->
                        if (list.isNotEmpty()) {
                            uiState = FeedUiState.Success(list = list)
                        }
                    }
                }
            } catch (e: Exception) {
                when (uiState) {
                    is FeedUiState.Error -> {
                        Log.e(tag, "TODO:: $uiState, ${e.message}")
                    }

                    FeedUiState.Loading -> {
                        uiState = FeedUiState.Error(e.message)
                    }

                    is FeedUiState.Success -> {
                        Log.e(tag, "TODO:: $uiState, ${e.message}")
                    }
                }
            }
        }
    }

    fun findIndexByReviewId(reviewId: Int): Int {
        val state = uiState
        if (state is FeedUiState.Success) {
            return state.list.indexOf(state.list.find { it.reviewId == reviewId })
        }
        return 0
    }

    override fun refreshFeed() {
        Log.d("__FeedScreenByRestaurantIdViewModel", "refreshFeed called but nothing to do")
    }

    override fun onBottom() {
        Log.d("__FeedScreenByRestaurantIdViewModel", "onBottom called but nothing to do")
    }
}
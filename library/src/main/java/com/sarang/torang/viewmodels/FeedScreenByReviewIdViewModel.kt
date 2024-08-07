package com.sarang.torang.viewmodels

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.sarang.torang.uistate.FeedUiState
import com.sarang.torang.usecase.AddFavoriteUseCase
import com.sarang.torang.usecase.AddLikeUseCase
import com.sarang.torang.usecase.DeleteFavoriteUseCase
import com.sarang.torang.usecase.DeleteLikeUseCase
import com.sarang.torang.usecase.FeedRefreshUseCase
import com.sarang.torang.usecase.GetFeedByRestaurantIdFlowUseCase
import com.sarang.torang.usecase.GetFeedByReviewIdUseCase
import com.sarang.torang.usecase.GetFeedFlowUseCase
import com.sarang.torang.usecase.GetMyFeedFlowUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedScreenByReviewIdViewModel @Inject constructor(
    feedRefreshUseCase: FeedRefreshUseCase,
    addLikeUseCase: AddLikeUseCase,
    deleteLikeUseCase: DeleteLikeUseCase,
    addFavoriteUseCase: AddFavoriteUseCase,
    deleteFavoriteUseCase: DeleteFavoriteUseCase,
    getFeedFlowUseCase: GetFeedFlowUseCase,
    private val getFeedByReviewIdUseCase: GetFeedByReviewIdUseCase,
) : FeedsViewModel(
    feedRefreshUseCase,
    addLikeUseCase,
    deleteLikeUseCase,
    addFavoriteUseCase,
    deleteFavoriteUseCase,
    getFeedFlowUseCase
) {
    fun getFeedByReviewId(reviewId: Int) {
        Log.d("__FeedScreenByReviewIdViewModel", "load feed by reviewId : ${reviewId}")
        _uiState.value = FeedUiState.Loading
        viewModelScope.launch {

            try {
                val result = getFeedByReviewIdUseCase.invoke(reviewId)

                _uiState.value = FeedUiState.Success(
                    list = listOf(result.copy(
                        onLike = { onLike(reviewId) },
                        onFavorite = { onFavorite(reviewId) }
                    )))
            } catch (e: Exception) {
                Log.e("__FeedScreenByReviewIdViewModel", e.message.toString())
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
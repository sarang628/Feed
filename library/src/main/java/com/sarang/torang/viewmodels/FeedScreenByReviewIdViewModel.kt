package com.sarang.torang.viewmodels

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.sarang.torang.uistate.FeedUiState
import com.sarang.torang.usecase.AddFavoriteUseCase
import com.sarang.torang.usecase.AddLikeUseCase
import com.sarang.torang.usecase.DeleteFavoriteUseCase
import com.sarang.torang.usecase.DeleteLikeUseCase
import com.sarang.torang.usecase.FeedWithPageUseCase
import com.sarang.torang.usecase.GetFeedByReviewIdUseCase
import com.sarang.torang.usecase.GetFeedFlowUseCase
import com.sarang.torang.usecase.IsLoginFlowForFeedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedScreenByReviewIdViewModel @Inject constructor(
    addLikeUseCase: AddLikeUseCase,
    deleteLikeUseCase: DeleteLikeUseCase,
    addFavoriteUseCase: AddFavoriteUseCase,
    deleteFavoriteUseCase: DeleteFavoriteUseCase,
    getFeedFlowUseCase: GetFeedFlowUseCase,
    feedWithPageUseCase: FeedWithPageUseCase,
    isLoginFlowUseCase: IsLoginFlowForFeedUseCase,
    private val getFeedByReviewIdUseCase: GetFeedByReviewIdUseCase,
) : FeedsViewModel(
    feedWithPageUseCase,
    addLikeUseCase,
    deleteLikeUseCase,
    addFavoriteUseCase,
    deleteFavoriteUseCase,
    getFeedFlowUseCase,
    isLoginFlowUseCase
) {
    fun getFeedByReviewId(reviewId: Int) {
        Log.d("__FeedScreenByReviewIdViewModel", "load feed by reviewId : $reviewId")
        uiState = FeedUiState.Loading
        viewModelScope.launch {

            try {
                val result = getFeedByReviewIdUseCase.invoke(reviewId)
                Log.d("__FeedScreenByReviewIdViewModel", "load feed by reviewId result : ${result}")
                uiState = FeedUiState.Success(
                    list = listOf(result.copy(
                        onLike = { onLike(reviewId) },
                        onFavorite = { onFavorite(reviewId) }
                    )))
            } catch (e: Exception) {
                uiState = FeedUiState.Error(e.message.toString())
                Log.e("__FeedScreenByReviewIdViewModel", e.message.toString())
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
        Log.d("__FeedScreenByReviewIdViewModel", "refreshFeed called but nothing to do")
    }

    override fun onBottom() {
        Log.d("__FeedScreenByReviewIdViewModel", "onBottom called but nothing to do")
    }
}
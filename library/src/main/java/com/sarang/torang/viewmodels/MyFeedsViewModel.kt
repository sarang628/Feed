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
import com.sarang.torang.usecase.GetFeedFlowUseCase
import com.sarang.torang.usecase.GetMyAllFeedByReviewIdUseCase
import com.sarang.torang.usecase.GetMyFeedFlowUseCase
import com.sarang.torang.usecase.GetUserAllFeedByReviewIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyFeedsViewModel @Inject constructor(
    addLikeUseCase: AddLikeUseCase,
    deleteLikeUseCase: DeleteLikeUseCase,
    addFavoriteUseCase: AddFavoriteUseCase,
    deleteFavoriteUseCase: DeleteFavoriteUseCase,
    getFeedFlowUseCase: GetFeedFlowUseCase,
    feedWithPageUseCase: FeedWithPageUseCase,
    private val getMyFeedFlowUseCase: GetMyFeedFlowUseCase,
    private val getMyAllFeedByReviewIdUseCase: GetMyAllFeedByReviewIdUseCase,
) : FeedsViewModel(
    feedWithPageUseCase,
    addLikeUseCase,
    deleteLikeUseCase,
    addFavoriteUseCase,
    deleteFavoriteUseCase,
    getFeedFlowUseCase
) {
    fun getUserFeedByReviewId(reviewId: Int) {
        Log.d("__MyFeedsViewModel", "get user feed by reviewId : $reviewId")
        uiState = FeedUiState.Loading
        viewModelScope.launch {
            getMyAllFeedByReviewIdUseCase.invoke(reviewId)
            getMyFeedFlowUseCase.invoke(reviewId).collect { list ->
                list.map { review ->
                    review.copy(
                        onLike = { onLike(review.reviewId) },
                        onFavorite = { onFavorite(review.reviewId) }
                    )
                }.let { list ->
                    if (list.isNotEmpty()) {
                        uiState = FeedUiState.Success(list = list)
                    } else {
                        uiState = FeedUiState.Error("피드가 없습니다.")
                    }
                }
            }
        }
    }

    override fun refreshFeed() {
        Log.d("__MyFeedsViewModel", "refreshFeed called but nothing to do")
    }

    override fun onBottom() {
        Log.d("__MyFeedsViewModel", "onBottom called but nothing to do")
    }


    fun findIndexByReviewId(reviewId: Int): Int {
        val state = uiState
        if (state is FeedUiState.Success) {
            return state.list.indexOf(state.list.find { it.reviewId == reviewId })
        }
        return 0
    }
}
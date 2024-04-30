package com.sarang.torang.viewmodels

import androidx.lifecycle.viewModelScope
import com.sarang.torang.baseFeedLog
import com.sarang.torang.uistate.FeedUiState
import com.sarang.torang.usecase.AddFavoriteUseCase
import com.sarang.torang.usecase.AddLikeUseCase
import com.sarang.torang.usecase.DeleteFavoriteUseCase
import com.sarang.torang.usecase.DeleteLikeUseCase
import com.sarang.torang.usecase.FeedRefreshUseCase
import com.sarang.torang.usecase.GetFeedFlowUseCase
import com.sarang.torang.usecase.GetMyFeedFlowUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyFeedsViewModel @Inject constructor(
    feedRefreshUseCase: FeedRefreshUseCase,
    addLikeUseCase: AddLikeUseCase,
    deleteLikeUseCase: DeleteLikeUseCase,
    addFavoriteUseCase: AddFavoriteUseCase,
    deleteFavoriteUseCase: DeleteFavoriteUseCase,
    getFeedFlowUseCase: GetFeedFlowUseCase,
    private val getMyFeedFlowUseCase: GetMyFeedFlowUseCase
) : FeedsViewModel(
    feedRefreshUseCase,
    addLikeUseCase,
    deleteLikeUseCase,
    addFavoriteUseCase,
    deleteFavoriteUseCase,
    getFeedFlowUseCase
) {
    fun getUserFeed(reviewId: Int) {
        baseFeedLog("getUserFeed: ${reviewId}")
        _uiState.value = FeedUiState.Loading
        viewModelScope.launch {
            getMyFeedFlowUseCase.invoke(reviewId).collect { list ->
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
        baseFeedLog("uiState is ${state}")
        if (state is FeedUiState.Success) {
            baseFeedLog(state.list.size.toString())
            return state.list.indexOf(state.list.find { it.reviewId == reviewId })
        }
        return 0
    }
}
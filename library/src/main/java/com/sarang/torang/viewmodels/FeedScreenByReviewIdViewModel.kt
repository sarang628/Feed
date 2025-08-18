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
    override val tag: String = "__FeedScreenByReviewIdViewModel"
    fun getFeedByReviewId(reviewId: Int) {
        //uiState = FeedUiState.Loading TODO:: 설정하기
        viewModelScope.launch {

            try {
                val result = getFeedByReviewIdUseCase.invoke(reviewId)
                //uiState = FeedUiState.Success(list = listOf(result)) TODO:: 설정하기
            } catch (e: Exception) {
                Log.e(tag, "getFeedByReviewId 실패 reviewId : $reviewId, error msg: ${e.message}")
                //uiState = FeedUiState.Error(e.message.toString()) TODO:: 설정하기
            }
        }
    }

    fun findIndexByReviewId(reviewId: Int): Int {
//        val state = uiState
//        if (state is FeedUiState.Success) {
//            return state.list.indexOf(state.list.find { it.reviewId == reviewId })
//        } TODO:: 설정하기
        return 0
    }

    override fun refreshFeed() {
        Log.d("__FeedScreenByReviewIdViewModel", "refreshFeed called but nothing to do")
    }

    override fun onBottom() {
        Log.d("__FeedScreenByReviewIdViewModel", "onBottom called but nothing to do")
    }
}
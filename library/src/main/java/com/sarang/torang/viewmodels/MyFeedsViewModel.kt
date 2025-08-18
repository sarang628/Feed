package com.sarang.torang.viewmodels

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.sarang.torang.uistate.FeedUiState
import com.sarang.torang.usecase.AddFavoriteUseCase
import com.sarang.torang.usecase.AddLikeUseCase
import com.sarang.torang.usecase.DeleteFavoriteUseCase
import com.sarang.torang.usecase.DeleteLikeUseCase
import com.sarang.torang.usecase.FeedWithPageUseCase
import com.sarang.torang.usecase.GetFeedFlowUseCase
import com.sarang.torang.usecase.GetMyAllFeedByReviewIdUseCase
import com.sarang.torang.usecase.GetMyFeedFlowUseCase
import com.sarang.torang.usecase.IsLoginFlowForFeedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
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
    isLoginFlowUseCase: IsLoginFlowForFeedUseCase,
) : FeedsViewModel(
    feedWithPageUseCase,
    addLikeUseCase,
    deleteLikeUseCase,
    addFavoriteUseCase,
    deleteFavoriteUseCase,
    getFeedFlowUseCase,
    isLoginFlowUseCase
) {
    fun getUserFeedByReviewId(reviewId: Int) {
        //uiState = FeedUiState.Loading TODO:: 설정하기
        viewModelScope.launch {
            getMyAllFeedByReviewIdUseCase.invoke(reviewId)
            getMyFeedFlowUseCase.invoke(reviewId).collect { list ->
                list.let { list ->
                    if (list.isNotEmpty()) {
                        //uiState = FeedUiState.Success(list = list) TODO:: 설정하기
                    } else {
                        //uiState = FeedUiState.Error("피드가 없습니다.") TODO:: 설정하기
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
//        val state = uiState
//        if (state is FeedUiState.Success) {
//            return state.list.indexOf(state.list.find { it.reviewId == reviewId })
//        } TODO:: 설정하기
        return 0
    }
}
package com.sarang.torang.viewmodels

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.sarang.torang.uistate.FeedUiState
import com.sarang.torang.uistate.FeedUiState.*
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
        //uiState = Loading TODO::로딩 설정하기
        viewModelScope.launch {
            try {
                getFeedByRestaurantIdFlowUseCase.invoke(restaurantId).collect { list ->
                    list.let { list ->
                        if (list.isNotEmpty()) {
                            //uiState = Success(list = list) TODO:: 데이터 설정하기
                        }
                    }
                }
            } catch (e: Exception) {
                when (uiState) {
//                    is Error -> { Log.e(tag, "TODO:: $uiState, ${e.message}") }
//                    is Loading -> { uiState = Error(e.message) }
//                    is Success -> { Log.e(tag, "TODO:: $uiState, ${e.message}") }
//                    Empty -> TODO()
                }
            }
        }
    }

    fun findIndexByReviewId(reviewId: Int): Int {
        //TODO:: 설정하기
//        val state = uiState
//        if (state is FeedUiState.Success) {
//            return state.list.indexOf(state.list.find { it.reviewId == reviewId })
//        }
        return 0
    }

    override fun refreshFeed() {
        Log.d("__FeedScreenByRestaurantIdViewModel", "refreshFeed called but nothing to do")
    }

    override fun onBottom() {
        Log.d("__FeedScreenByRestaurantIdViewModel", "onBottom called but nothing to do")
    }
}
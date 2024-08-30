package com.sarang.torang.viewmodels

import android.util.Log
import androidx.annotation.MainThread
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sarang.torang.uistate.FeedUiState
import com.sarang.torang.usecase.AddFavoriteUseCase
import com.sarang.torang.usecase.AddLikeUseCase
import com.sarang.torang.usecase.DeleteFavoriteUseCase
import com.sarang.torang.usecase.DeleteLikeUseCase
import com.sarang.torang.usecase.FeedWithPageUseCase
import com.sarang.torang.usecase.GetFeedFlowUseCase
import com.sarang.torang.usecase.IsLoginFlowForFeedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class FeedsViewModel @Inject constructor(
    private val feedWithPageUseCase: FeedWithPageUseCase,
    private val addLikeUseCase: AddLikeUseCase,
    private val deleteLikeUseCase: DeleteLikeUseCase,
    private val addFavoriteUseCase: AddFavoriteUseCase,
    private val deleteFavoriteUseCase: DeleteFavoriteUseCase,
    private val getFeedFlowUseCase: GetFeedFlowUseCase,
    private val isLoginFlowUseCase: IsLoginFlowForFeedUseCase,
) : ViewModel() {
    var uiState: FeedUiState by mutableStateOf(FeedUiState.Loading)
    private var initializeCalled = false
    private var page = 0
    val isLogin = isLoginFlowUseCase.isLogin

    var isRefreshing by mutableStateOf(false)
        private set

    val TAG = "__FeedsViewModel"

    @MainThread
    fun initialize() {
        if (initializeCalled) return
        initializeCalled = true

        Log.d("__FeedsViewModel", "initialize")
        viewModelScope.launch {
            try {
                feedWithPageUseCase.invoke(page)
                page++
            } catch (e: Exception) {
                uiState = FeedUiState.Error(e.message)
            }

            getFeedFlowUseCase
                .invoke()
                .collect { list ->
                    Log.d("__FeedsViewModel", "received feed list : ${list.size}")
                    uiState = FeedUiState.Success(list = list.map { review ->
                        review.copy(
                            onLike = { onLike(review.reviewId) },
                            onFavorite = { onFavorite(review.reviewId) }
                        )
                    })
                }
        }
    }

    // 피드 리스트 갱신
    open fun refreshFeed() {
        Log.d(TAG, "refreshFeed")
        viewModelScope.launch {
            isRefreshing = true
            try {
                page = 0
                feedWithPageUseCase.invoke(page)
                page++
            } catch (e: Exception) {
                handleErrorMsg(e)
            } finally {
                isRefreshing = false
            }
        }
    }

    // 즐겨찾기 클릭
    internal fun onFavorite(reviewId: Int) {
        if (uiState is FeedUiState.Success) {
            viewModelScope.launch {
                val review =
                    (uiState as FeedUiState.Success).list.find { it.reviewId == reviewId }
                review?.let {
                    try {
                        if (it.isFavorite) {
                            deleteFavoriteUseCase.invoke(reviewId)
                        } else {
                            addFavoriteUseCase.invoke(reviewId)
                        }
                    } catch (e: Exception) {
                        handleErrorMsg(e)
                    }
                }
            }
        }
    }

    // 좋아여 클릭
    internal fun onLike(reviewId: Int) {
        if (uiState is FeedUiState.Success) {

            viewModelScope.launch {
                val review =
                    (uiState as FeedUiState.Success).list.find { it.reviewId == reviewId }
                review?.let {
                    try {
                        if (it.isLike) {
                            deleteLikeUseCase.invoke(reviewId)
                        } else {
                            addLikeUseCase.invoke(reviewId)
                        }
                    } catch (e: Exception) {
                        handleErrorMsg(e)
                    }
                }
            }
        }
    }

    private fun handleErrorMsg(e: Exception) {
        if (uiState is FeedUiState.Success) {
            uiState = (uiState as FeedUiState.Success).copy(msg = e.message)
        }
    }


    // 에러메시지 삭제
    fun clearErrorMsg() {
        if (uiState is FeedUiState.Success) {
            uiState = (uiState as FeedUiState.Success).copy(msg = null)
        }
    }

    open fun onBottom() {
        viewModelScope.launch {
            try {
                Log.d(TAG, "called onBottom. request $page pages.")
                feedWithPageUseCase.invoke(page)
                page++
            } catch (e: Exception) {
                handleErrorMsg(e)
            } finally {
            }
        }
    }
}
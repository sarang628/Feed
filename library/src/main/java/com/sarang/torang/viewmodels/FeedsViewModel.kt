package com.sarang.torang.viewmodels

import android.util.Log
import androidx.annotation.MainThread
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sarang.torang.uistate.FeedUiState
import com.sarang.torang.usecase.AddFavoriteUseCase
import com.sarang.torang.usecase.AddLikeUseCase
import com.sarang.torang.usecase.DeleteFavoriteUseCase
import com.sarang.torang.usecase.DeleteLikeUseCase
import com.sarang.torang.usecase.FeedRefreshUseCase
import com.sarang.torang.usecase.GetFeedFlowUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

val TAG = "_FeedsViewModel"

@HiltViewModel
class FeedsViewModel @Inject constructor(
    private val feedRefreshUseCase: FeedRefreshUseCase,
    private val addLikeUseCase: AddLikeUseCase,
    private val deleteLikeUseCase: DeleteLikeUseCase,
    private val addFavoriteUseCase: AddFavoriteUseCase,
    private val deleteFavoriteUseCase: DeleteFavoriteUseCase,
    private val getFeedFlowUseCase: GetFeedFlowUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<FeedUiState> = MutableStateFlow(FeedUiState.Loading)
    val uiState = _uiState.asStateFlow()
    private var initializeCalled = false

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()

    @MainThread
    fun initialize() {
        if (initializeCalled) return
        initializeCalled = true
        viewModelScope.launch {
            try {
                feedRefreshUseCase.invoke()
            } catch (e: Exception) {
                _uiState.update { FeedUiState.Error(e.message) }
            }

            getFeedFlowUseCase
                .invoke()
                .collect { list ->
                    _uiState.update { uiState ->
                        FeedUiState.Success(list = list.map { review ->
                            review.copy(
                                onLike = { onLike(review.reviewId) },
                                onFavorite = { onFavorite(review.reviewId) }
                            )
                        })
                    }
                }
        }
    }

    // 피드 리스트 갱신
    fun refreshFeed() {
        viewModelScope.launch {
            _isRefreshing.update { true }
            try {
                feedRefreshUseCase.invoke()
            } catch (e: Exception) {
                _uiState.update { FeedUiState.Error(e.message) }
            } finally {
                _isRefreshing.update { false }
            }
        }
    }

    // 즐겨찾기 클릭
    private fun onFavorite(reviewId: Int) {
        if (uiState.value is FeedUiState.Success) {
            viewModelScope.launch {
                val review =
                    (uiState.value as FeedUiState.Success).list.find { it.reviewId == reviewId }
                review?.let {
                    try {
                        if (it.isFavorite) {
                            deleteFavoriteUseCase.invoke(reviewId)
                        } else {
                            addFavoriteUseCase.invoke(reviewId)
                        }
                    } catch (e: Exception) {
                        _uiState.update { FeedUiState.Error(e.message) }
                    }
                }
            }
        }
    }

    // 좋아여 클릭
    private fun onLike(reviewId: Int) {
        if (uiState.value is FeedUiState.Success) {

            viewModelScope.launch {
                val review =
                    (uiState.value as FeedUiState.Success).list.find { it.reviewId == reviewId }
                review?.let {
                    try {
                        if (it.isLike) {
                            deleteLikeUseCase.invoke(reviewId)
                        } else {
                            addLikeUseCase.invoke(reviewId)
                        }
                    } catch (e: Exception) {
                        _uiState.update { FeedUiState.Error(e.message) }
                    }
                }
            }
        }
    }


    // 에러메시지 삭제
    fun clearErrorMsg() {

    }

    fun onBottom() {

    }
}
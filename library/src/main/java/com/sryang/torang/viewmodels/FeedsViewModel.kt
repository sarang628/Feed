package com.sryang.torang.viewmodels

import androidx.annotation.MainThread
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sryang.torang.uistate.FeedUiState
import com.sryang.torang.usecase.AddFavoriteUseCase
import com.sryang.torang.usecase.AddLikeUseCase
import com.sryang.torang.usecase.DeleteFavoriteUseCase
import com.sryang.torang.usecase.DeleteLikeUseCase
import com.sryang.torang.usecase.FeedRefreshUseCase
import com.sryang.torang.usecase.GetFeedFlowUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedsViewModel @Inject constructor(
    private val feedRefreshUseCase: FeedRefreshUseCase,
    private val addLikeUseCase: AddLikeUseCase,
    private val deleteLikeUseCase: DeleteLikeUseCase,
    private val addFavoriteUseCase: AddFavoriteUseCase,
    private val deleteFavoriteUseCase: DeleteFavoriteUseCase,
    private val getFeedFlowUseCase: GetFeedFlowUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(FeedUiState(isRefreshing = true))
    val uiState = _uiState.asStateFlow()
    private var initializeCalled = false

    @MainThread
    fun initialize() {
        if (initializeCalled) return
        initializeCalled = true
        viewModelScope.launch {
            getFeedFlowUseCase.invoke().collect { list ->
                _uiState.update { it.copy(list = list) }
            }
        }
        refreshFeed()
    }

    // 피드 리스트 갱신
    fun refreshFeed() {
        viewModelScope.launch {
            _uiState.update { it.copy(isRefreshing = true) }
            try {
                feedRefreshUseCase.invoke()
                _uiState.update { it.copy(error = "피드를 가져왔습니다.") }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message) }
            } finally {
                _uiState.update { it.copy(isRefreshing = false, isLoaded = true) }
            }
        }
    }

    // 피드 가져오기
    private suspend fun getFeed() {

    }


    // 즐겨찾기 클릭
    fun onFavorite(reviewId: Int) {
        viewModelScope.launch {
            val review = uiState.value.list.find { it.reviewId == reviewId }
            review?.let {
                try {
                    if (it.isFavorite) {
                        deleteFavoriteUseCase.invoke(reviewId)
                    } else {
                        addFavoriteUseCase.invoke(reviewId)
                    }
                } catch (e: Exception) {
                    _uiState.update { it.copy(error = e.message) }
                }
            }
        }
    }

    // 좋아여 클릭
    fun onLike(reviewId: Int) {
        viewModelScope.launch {
            val review = uiState.value.list.find { it.reviewId == reviewId }
            review?.let {
                try {
                    if (it.isLike) {
                        deleteLikeUseCase.invoke(reviewId)
                    } else {
                        addLikeUseCase.invoke(reviewId)
                    }
                } catch (e: Exception) {
                    _uiState.update { it.copy(error = e.message) }
                }
            }
        }
    }


    // 에러메시지 삭제
    fun clearErrorMsg() {
        _uiState.update { it.copy(error = null) }
    }

    fun onBottom() {

    }
}
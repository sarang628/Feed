package com.sryang.torang.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sryang.torang.uistate.FeedUiState
import com.sryang.torang.usecase.FeedService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedsViewModel @Inject constructor(private val feedService: FeedService) : ViewModel() {

    // UIState
    private val _uiState = MutableStateFlow(FeedUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            feedService.feeds.collect { newData -> _uiState.update { it.copy(list = newData, error = "피드를 가져왔습니다.") } } // feed 리스트 수집
        }
        refreshFeed()
    }

    // 피드 리스트 갱신
    fun refreshFeed() {
        viewModelScope.launch {
            _uiState.update { it.copy(isRefreshing = true) }
            getFeed() // feed 가져오기
            _uiState.update { it.copy(isRefreshing = false) }
        }
    }

    // 피드 가져오기
    private suspend fun getFeed() {
        try {
            feedService.getFeeds()
        } catch (e: Exception) {
            Log.e("FeedsViewModel", e.toString())
        }
    }


    // 즐겨찾기 클릭
    fun onFavorite(reviewId: Int) {
        viewModelScope.launch {
            val review = uiState.value.list.find { it.reviewId == reviewId }
            review?.let {
                try {
                    if (it.isFavorite) {
                        feedService.deleteFavorite(reviewId)
                    } else {
                        feedService.addFavorite(reviewId)
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
                        feedService.deleteLike(reviewId)
                    } else {
                        feedService.addLike(reviewId)
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
}
package com.example.screen_feed

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.net.UnknownHostException
import javax.inject.Inject

data class AA(val a: Int) {

}

@HiltViewModel
class FeedsViewModel @Inject constructor(
    private val feedService: FeedService
) : ViewModel() {

    private val _uiState = MutableStateFlow<FeedUiState>(
        FeedUiState()
    )

    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            feedService.feeds1.collect { newData ->
                Log.d("FeedsViewModel", newData.toString())
                _uiState.emit(
                    _uiState.value.copy(
                        list = newData
                    )
                )
            }
            try {
                feedService.getFeeds(1)
            } catch (e: UnknownHostException) {
                Log.e("FeedsViewModel", e.toString())
            } catch (e: Exception) {
                Log.e("FeedsViewModel", e.toString())
            }
        }
    }

    fun clickLike(id: Int) {
        Log.d("FeedsViewModel", id.toString())
    }

    fun clickFavorite(id: Int) {
    }

    fun refreshFeed() {
        viewModelScope.launch {
            _uiState.emit(
                _uiState.value.copy(
                    isRefreshing = true
                )
            )
            try {
                feedService.getFeeds(1)
            } catch (e: UnknownHostException) {
                Log.e("FeedsViewModel", e.toString())
                _uiState.emit(
                    uiState.value.copy(isFailedLoadFeed = true)
                )
            } catch (e: Exception) {
                Log.e("FeedsViewModel", e.toString())
            }

            _uiState.emit(
                _uiState.value.copy(
                    isRefreshing = false
                )
            )
        }
    }

    fun onBottom() {
        Log.d("sryang123", "onBottom!")
    }

    fun onComment(reviewId: Int) {
        viewModelScope.launch {
            val list: List<CommentData> = feedService.getComment(reviewId)
            _uiState.emit(
                uiState.value.copy(
                    selectedReviewId = reviewId,
                    isExpandCommentBottomSheet = true,
                    comments = list
                )
            )
        }
    }

    fun onShare() {
        viewModelScope.launch {
            _uiState.emit(uiState.value.copy(isShareCommentBottomSheet = true))
        }
    }

    fun onFavorite(reviewId: Int) {
        viewModelScope.launch {
            val review = uiState.value.list.find { it.reviewId == reviewId }
            review?.let {
                Log.d("FeedsViewModel", it.isFavorite.toString())
                if (it.isFavorite) {
                    feedService.deleteFavorite(1, reviewId)
                } else {
                    feedService.addFavorite(1, reviewId)
                }
            }
        }
    }

    fun onLike(reviewId: Int) {
        viewModelScope.launch {
            val review = uiState.value.list.find { it.reviewId == reviewId }
            review?.let {
                if (it.isLike) {
                    feedService.deleteLike(1, reviewId)
                } else {
                    feedService.addLike(1, reviewId)
                }
            }
        }
    }

    fun closeMenu() {
        viewModelScope.launch {
            _uiState.emit(uiState.value.copy(isExpandMenuBottomSheet = false))
        }
    }

    fun closeComment() {
        viewModelScope.launch {
            _uiState.emit(
                uiState.value.copy(isExpandCommentBottomSheet = false, selectedReviewId = null)
            )
        }
    }

    fun closeShare() {
        viewModelScope.launch {
            _uiState.emit(uiState.value.copy(isShareCommentBottomSheet = false))
        }
    }

    fun onMenu() {
        viewModelScope.launch {
            _uiState.emit(uiState.value.copy(isExpandMenuBottomSheet = true))
        }
    }

    fun consumeFailedLoadFeedSnackBar() {
        viewModelScope.launch {
            _uiState.emit(uiState.value.copy(isFailedLoadFeed = false))
        }
    }

    fun sendComment(comment: String) {
        viewModelScope.launch {
            uiState.value.selectedReviewId?.let { reviewId ->
                feedService.addComment(reviewId = reviewId, comment = comment, userId = 1)
            }

        }
    }

}
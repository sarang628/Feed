package com.sryang.torang.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.sryang.torang.data.CommentData
import com.sryang.torang.uistate.FeedUiState
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

    private val _uiState = MutableStateFlow(
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
                feedService.getFeeds()
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
                feedService.getFeeds()
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
                try {
                    if (it.isFavorite) {
                        feedService.deleteFavorite(reviewId)
                    } else {
                        feedService.addFavorite(reviewId)
                    }
                } catch (e: Exception) {
                    _uiState.emit(uiState.value.copy(error = e.message))
                }
            }
        }
    }

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
                    _uiState.emit(uiState.value.copy(error = e.message))
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
                feedService.addComment(reviewId = reviewId, comment = comment)
            }

        }
    }

    fun removeErrorMsg() {
        viewModelScope.launch {
            _uiState.emit(
                uiState.value.copy(
                    error = null
                )
            )
        }
    }

}
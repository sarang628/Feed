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
                feedService.getFeeds(HashMap())
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
                feedService.getFeeds(HashMap())
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

    fun onComment() {
        viewModelScope.launch {
            _uiState.emit(uiState.value.copy(isExpandCommentBottomSheet = true))
        }
    }

    fun onShare() {
        viewModelScope.launch {
            _uiState.emit(uiState.value.copy(isShareCommentBottomSheet = true))
        }
    }

    fun onFavorite() {


    }

    fun onLike() {


    }

    fun closeMenu() {
        viewModelScope.launch {
            _uiState.emit(uiState.value.copy(isExpandMenuBottomSheet = false))
        }
    }

    fun closeComment() {
        viewModelScope.launch {
            _uiState.emit(uiState.value.copy(isExpandCommentBottomSheet = false))
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

}
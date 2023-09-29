package com.example.screen_feed

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
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
            feedService.getFeeds(HashMap())
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
            val result = feedService.getFeeds(HashMap())

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

}
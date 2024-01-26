package com.sarang.torang.uistate

import com.sarang.torang.data.feed.Feed
import java.util.Objects

sealed interface FeedUiState {
    data class Error(val msg: String?) : FeedUiState
    data class Success(val list: List<Feed>, val msg: String? = null) : FeedUiState
    object Loading : FeedUiState
}
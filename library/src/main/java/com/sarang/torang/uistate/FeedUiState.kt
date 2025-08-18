package com.sarang.torang.uistate

import com.sarang.torang.data.feed.Feed
import java.util.Objects

// @formatter:off
sealed interface FeedUiState {
    data class Error(val msg: String?) : FeedUiState
    data class Success(val list: List<Feed>) : FeedUiState
    object Loading : FeedUiState
    object Empty : FeedUiState
}
// @formatter:on
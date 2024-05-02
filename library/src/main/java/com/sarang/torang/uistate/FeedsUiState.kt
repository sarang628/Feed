package com.sarang.torang.uistate

import com.sarang.torang.data.feed.Feed

sealed interface FeedsUiState {
    object Loading : FeedsUiState
    object Empty : FeedsUiState
    data class Success(val reviews: List<Feed>) : FeedsUiState
    data class Error(val message: String) : FeedsUiState
}
package com.sarang.torang.compose.feed

sealed interface FeedLoadingUiState {
    data class  Error   (val msg: String?)  : FeedLoadingUiState
    object      Success                     : FeedLoadingUiState
    object      Loading                     : FeedLoadingUiState
    object      Empty                       : FeedLoadingUiState
    object      Reconnect                   : FeedLoadingUiState
}
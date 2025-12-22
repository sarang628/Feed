package com.sarang.torang.uistate

sealed interface FeedLoadingUiState {
    data class  Error   (val msg: String?)  : FeedLoadingUiState
    object      Success                     : FeedLoadingUiState
    object      Loading                     : FeedLoadingUiState
    object      Empty                       : FeedLoadingUiState
    object      Reconnect                   : FeedLoadingUiState
}
package com.example.screen_feed.uistate

import com.example.screen_feed.usecase.ItemFeedUIState

data class FeedsUIstate(
    val isRefresh: Boolean,
    val isEmptyFeed: Boolean,
    val toastMsg: String? = null,
    val feedItemUiState: ArrayList<ItemFeedUIState>? = null
)
package com.example.screen_feed

data class FeedUiState(
    val isRefreshing: Boolean = false,
    val list: List<FeedData> = ArrayList(),
    val isExpandMenuBottomSheet: Boolean = false,
    val isExpandCommentBottomSheet: Boolean = false,
    val isShareCommentBottomSheet: Boolean = false,
    val isFailedLoadFeed: Boolean = false
)
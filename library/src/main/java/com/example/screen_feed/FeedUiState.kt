package com.example.screen_feed

data class FeedUiState(val isRefreshing: Boolean = false, val list: List<FeedData> = ArrayList())
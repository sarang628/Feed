package com.example.screen_feed

data class FeedsUIstate(
    val isRefresh: Boolean,
    val isEmptyFeed: Boolean,
    val toastMsg: String? = null
)
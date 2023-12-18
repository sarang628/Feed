package com.sryang.torang.uistate

import com.sryang.torang.data.feed.Feed

data class FeedUiState(
    val isRefreshing: Boolean = false   // 스크롤 리프레시
    , val list: List<Feed> = listOf()   // 피드 리스트
    , val error: String? = null         // 에러 메시지
    , val isLoaded: Boolean = false
)

val FeedUiState.isEmpty: Boolean get() = this.list.isEmpty() && isLoaded

val FeedUiState.showShimmer: Boolean get() = false